package use_case.vote;

import data_access.room.InMemoryRoomDataAccessObject;
import entity.Ballot;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class VoteInteractorTest {

    private static class TestPresenter implements VoteOutputBoundary {
        VoteOutputData lastOutput;
        String lastFailure;

        @Override
        public void present(VoteOutputData outputData) {
            this.lastOutput = outputData;
        }

        @Override
        public void presentFailure(String message) {
            this.lastFailure = message;
        }
    }

    @Test
    public void submitValidBallot_savesAndReportsCount() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject();
        dao.addMovie("m1");
        dao.addMovie("m2");

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("m2", "m1"));
        interactor.submitBallot(input);

        List<Ballot> ballots = dao.getBallots();
        assertEquals(1, ballots.size(), "Ballot should be saved in DAO");
        assertNotNull(presenter.lastOutput, "Presenter should receive output update");
        assertEquals(1, presenter.lastOutput.getBallotsReceivedCount());
    }

    @Test
    public void submitInvalidBallot_isRejected() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject();
        // no movies in shortlist

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("nonexistent"));
        interactor.submitBallot(input);

        List<Ballot> ballots = dao.getBallots();
        assertEquals(0, ballots.size(), "Invalid ballot should not be saved");
        assertNotNull(presenter.lastFailure, "Presenter should receive failure");
    }

    @Test
    public void computeWinner_bordaCountsAndTieBreaksByShortlistOrder() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject();
        // shortlist order: A, B, C
        dao.addMovie("A");
        dao.addMovie("B");
        dao.addMovie("C");

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        // p1: A > B > C (A:3, B:2, C:1)
        interactor.submitBallot(new VoteInputData("p1", Arrays.asList("A", "B", "C")));
        // p2: B > A > C (B:3, A:2, C:1) -> totals A:5, B:5, C:2 -> tie A/B -> A earlier
        interactor.submitBallot(new VoteInputData("p1", Arrays.asList("A", "B", "C")));
        // p2: B > A > C (B:3, A:2, C:1) -> totals A:5, B:5, C:2 -> tie A/B -> A earlier
        interactor.submitBallot(new VoteInputData("p2", Arrays.asList("B", "A", "C")));

        // Ensure participants exist and host is p1
        dao.addParticipant("p1", "P1");
        dao.addParticipant("p2", "P2");

        interactor.computeWinner("p1");

        assertNotNull(presenter.lastOutput, "Presenter should receive final output");
        VoteOutputData out = presenter.lastOutput;
        assertEquals("A", out.getWinnerMovieId(), "Tie broken by shortlist order should choose A");

        Map<String, Integer> expected = new HashMap<>();
        expected.put("A", 5);
        expected.put("B", 5);
        expected.put("C", 2);
        assertEquals(expected, out.getScores());
    }
}
