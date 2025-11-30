package use_case.winner;

import data_access.room.InMemoryRoomDataAccessObject;
import data_access.tmdb.TmdbMovieGateway;
import entity.Ballot;
import entity.Movie;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class WinnerInteractorTest {

    private static class StubTmdbGateway extends TmdbMovieGateway {
        @Override
        public Movie fetchDetails(String movieId, String appendToResponse) throws IOException {
            // Return a simple Movie object without requiring a real API key / network
            return new Movie(movieId, "Title" + movieId, "2024", null, null, "en", 0.0);
        }
    }

    private static class TestPresenter implements WinnerOutputBoundary {
        WinnerOutputData last;
        String failure;

        @Override
        public void present(WinnerOutputData data) {
            this.last = data;
        }

        @Override
        public void presentFailure(String message) {
            this.failure = message;
        }
    }

    // Adapter to make InMemoryRoomDataAccessObject compatible with
    // WinnerInteractor's expected RoomDatabase
    private static class InMemoryRoomAdapter extends data_access.room.RoomDatabase {
        private final InMemoryRoomDataAccessObject dao;

        InMemoryRoomAdapter(InMemoryRoomDataAccessObject dao) {
            super("");
            this.dao = dao;
        }

        @Override
        public void refreshRoom() throws data_access.note_database.DataAccessException {
            dao.refreshRoom();
        }

        @Override
        public java.util.List<String> getShortlist() throws data_access.note_database.DataAccessException {
            return dao.getShortlist();
        }

        @Override
        public java.util.List<entity.Ballot> getBallots() throws data_access.note_database.DataAccessException {
            return dao.getBallots();
        }
    }

    @Test
    public void computeWinner_bordaCountsAndTieBreaksByShortlistOrder() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("host", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("A");
            dao.addMovie("B");
            dao.addMovie("C");
            // ballots: p1 A>B>C ; p2 B>A>C -> A:5, B:5, C:2 tie A/B -> shortlist order
            // chooses A
            dao.saveBallot(new Ballot("p1", Arrays.asList("A", "B", "C")));
            dao.saveBallot(new Ballot("p2", Arrays.asList("B", "A", "C")));
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
        TestPresenter presenter = new TestPresenter();
        InMemoryRoomAdapter adapter = new InMemoryRoomAdapter(dao);
        WinnerInteractor interactor = new WinnerInteractor(adapter, presenter, new StubTmdbGateway());
        interactor.computeWinner();
        assertNotNull(presenter.last, "Presenter should receive output");
        assertNull(presenter.failure, "Should not report failure");
        assertEquals("A", presenter.last.getWinnerMovieId(), "Tie should prefer shortlist order (A)");
        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 5);
        expected.put("B", 5);
        expected.put("C", 2);
        assertEquals(expected, presenter.last.getScores());
    }
}
