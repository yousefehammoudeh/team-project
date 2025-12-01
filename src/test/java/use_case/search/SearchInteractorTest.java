package use_case.search;

import org.junit.jupiter.api.Test;
import use_case.search.*;
import entity.Movie;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class SearchInteractorTest {

    // Helper to make fake movies
    private Movie makeMovie(String title) {
        return new Movie("id-" + title, title, "2020", "", List.of(), "en", 5.0);
    }

    // Test search with less than 5 results
    @Test
    void testSuccessfulSearchUnderFive() {

        // Fake Gateway
        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override
            public List<Movie> search(String title) {
                return List.of(makeMovie("A"), makeMovie("B"));
            }

            @Override
            public Movie fetchDetails(String id, String append) {
                return null; // unused
            }
        };

        // Capture output
        final List<Movie> captured = new ArrayList<>();
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void present(SearchOutputData data) {
                captured.addAll(data.getMovies());
            }

            @Override
            public void presentFailure(String error) {
                fail("Should not fail");
            }

            @Override
            public void switchToShortlistView() {}

            @Override
            public void switchToCreatedRoomView() {}
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.execute(new SearchInputData("test"));

        assertEquals(2, captured.size());
        assertEquals("A", captured.get(0).getTitle());
    }

    // Test search for exactly 5 movies
    @Test
    void testSuccessfulSearchExactlyFive() {

        // Fake Gateway
        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override
            public List<Movie> search(String title) {
                return List.of(makeMovie("A"), makeMovie("B"), makeMovie("C"),
                        makeMovie("D"), makeMovie("E"));
            }

            @Override
            public Movie fetchDetails(String id, String append) {
                return null; // unused
            }
        };

        // Capture output
        final List<Movie> captured = new ArrayList<>();
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void present(SearchOutputData data) {
                captured.addAll(data.getMovies());
            }

            @Override
            public void presentFailure(String error) {
                fail("Should not fail");
            }

            @Override
            public void switchToShortlistView() {}

            @Override
            public void switchToCreatedRoomView() {}
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.execute(new SearchInputData("test"));

        assertEquals(5, captured.size());
        assertEquals("A", captured.get(0).getTitle());
    }

    // Test search with more than 5 results
    @Test
    void testSearchTrimsToFiveResults() {

        // Create 8 fake movies
        List<Movie> fakeMovies = new ArrayList<>();
        for (int i = 0; i < 8; i++) fakeMovies.add(makeMovie("M" + i));

        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override
            public List<Movie> search(String title) {
                return fakeMovies;
            }

            @Override
            public Movie fetchDetails(String id, String append) {
                return null;
            }
        };

        final List<Movie> captured = new ArrayList<>();
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void present(SearchOutputData data) {
                captured.addAll(data.getMovies());
            }

            @Override
            public void presentFailure(String error) {
                fail("Should not fail");
            }

            @Override
            public void switchToShortlistView() {}

            @Override
            public void switchToCreatedRoomView() {}
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.execute(new SearchInputData("hello"));

        // Should trim to 5
        assertEquals(5, captured.size());
    }

    // Test search failure
    @Test
    void testSearchFailure() {

        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override
            public List<Movie> search(String title) {
                throw new RuntimeException("error!");
            }

            @Override
            public Movie fetchDetails(String id, String append) {
                return null;
            }
        };

        final List<String> errors = new ArrayList<>();
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override
            public void present(SearchOutputData data) {
                fail("Should not succeed");
            }

            @Override
            public void presentFailure(String error) {
                errors.add(error);
            }

            @Override
            public void switchToShortlistView() {}

            @Override
            public void switchToCreatedRoomView() {}
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.execute(new SearchInputData("test"));

        assertEquals(1, errors.size());
        assertTrue(errors.get(0).contains("Search failed"));
    }

    // Switch to shortlist view test
    @Test
    void testSwitchToShortlistView() {
        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override
            public List<Movie> search(String title) { return List.of(); }
            @Override
            public Movie fetchDetails(String id, String append) { return null; }
        };

        final boolean[] called = {false};
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override public void present(SearchOutputData d) {}
            @Override public void presentFailure(String error) {}
            @Override public void switchToShortlistView() { called[0] = true; }
            @Override public void switchToCreatedRoomView() {}
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.switchToShortlistView();

        assertTrue(called[0]);
    }

    // Switch to created room view test
    @Test
    void testSwitchToCreatedRoomView() {
        SearchUserDataAccessInterface gateway = new SearchUserDataAccessInterface() {
            @Override public List<Movie> search(String title) { return List.of(); }
            @Override public Movie fetchDetails(String id, String append) { return null; }
        };

        final boolean[] called = {false};
        SearchOutputBoundary presenter = new SearchOutputBoundary() {
            @Override public void present(SearchOutputData d) {}
            @Override public void presentFailure(String error) {}
            @Override public void switchToShortlistView() {}
            @Override public void switchToCreatedRoomView() { called[0] = true; }
        };

        SearchInteractor interactor = new SearchInteractor(gateway, presenter);

        interactor.switchToCreatedRoomView();

        assertTrue(called[0]);
    }
}
