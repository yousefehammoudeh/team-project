package use_case.vote;

import data_access.room.InMemoryRoomDataAccessObject;
import entity.Ballot;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

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
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("m1");
            dao.addMovie("m2");
            // Voting now requires the shortlist to be locked
            dao.setLocked(true);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("m2", "m1"));
        interactor.submitBallot(input);

        try {
            List<Ballot> ballots = dao.getBallots();
            assertEquals(1, ballots.size(), "Ballot should be saved in DAO");
        } catch (Exception e) {
            fail("Failed to get ballots: " + e.getMessage());
        }
        assertNotNull(presenter.lastOutput, "Presenter should receive output update");
        assertEquals(1, presenter.lastOutput.getBallotsReceivedCount());
    }

    @Test
    public void submitInvalidBallot_isRejected() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            // Lock even though there are no movies to emphasize invalid ballot reason
            dao.setLocked(true);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }
        // no movies in shortlist

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("nonexistent"));
        interactor.submitBallot(input);

        try {
            List<Ballot> ballots = dao.getBallots();
            assertEquals(0, ballots.size(), "Invalid ballot should not be saved");
        } catch (Exception e) {
            fail("Failed to get ballots: " + e.getMessage());
        }
        assertNotNull(presenter.lastFailure, "Presenter should receive failure");
    }

    @Test
    public void submitNullInput_presentsFail() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        interactor.submitBallot(null);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure for null input");
        assertEquals("Invalid ballot input", presenter.lastFailure);
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void submitWhenNotLocked_presentsFail() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("m1");
            dao.addMovie("m2");
            // Do NOT lock the shortlist
            dao.setLocked(false);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m2"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure when not locked");
        assertTrue(presenter.lastFailure.contains("not locked"), "Failure message should mention lock");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void submitPartialRanking_presentsFail() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("m1");
            dao.addMovie("m2");
            dao.addMovie("m3");
            dao.setLocked(true);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        // Only rank 2 out of 3 movies
        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m2"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure for incomplete ranking");
        assertTrue(presenter.lastFailure.contains("rank all movies"),
                "Failure message should mention ranking all movies");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void submitBallotWithInvalidMovie_presentsFail() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("m1");
            dao.addMovie("m2");
            dao.setLocked(true);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        // Include a movie not in shortlist
        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m3"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure for invalid movie");
        assertTrue(presenter.lastFailure.contains("invalid"), "Failure message should mention invalid ballot");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void submitBallotWithDuplicates_presentsFail() {
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("testUser", new HashMap<>());
        try {
            dao.createRoom("testRoom");
            dao.addMovie("m1");
            dao.addMovie("m2");
            dao.setLocked(true);
        } catch (Exception e) {
            fail("Setup failed: " + e.getMessage());
        }

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(dao, presenter);

        // Duplicate movie in ranking
        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m1"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure for duplicate movies");
        assertTrue(presenter.lastFailure.contains("invalid"), "Failure message should mention invalid ballot");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void saveBallotFails_presentsFail() {
        // Create a gateway that simulates save failure
        VoteUserDataAccessInterface failingGateway = new VoteUserDataAccessInterface() {
            @Override
            public boolean isLocked() {
                return true;
            }

            @Override
            public List<String> getShortlist() throws use_case.UseCaseDataAccessException {
                return Arrays.asList("m1", "m2");
            }

            @Override
            public boolean saveBallot(Ballot ballot) throws use_case.UseCaseDataAccessException {
                return false; // Simulate save failure
            }

            @Override
            public List<Ballot> getBallots() throws use_case.UseCaseDataAccessException {
                return Arrays.asList();
            }

            @Override
            public int participantsCount() throws use_case.UseCaseDataAccessException {
                return 2;
            }

            @Override
            public boolean isHost() throws use_case.UseCaseDataAccessException {
                return false;
            }
        };

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(failingGateway, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m2"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure when save fails");
        assertTrue(presenter.lastFailure.contains("Failed to save"), "Failure message should mention save failure");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

    @Test
    public void databaseException_presentsFail() {
        // Create a gateway that throws database exception
        VoteUserDataAccessInterface throwingGateway = new VoteUserDataAccessInterface() {
            @Override
            public boolean isLocked() throws use_case.UseCaseDataAccessException {
                throw new use_case.UseCaseDataAccessException("Database connection error");
            }

            @Override
            public List<String> getShortlist() throws use_case.UseCaseDataAccessException {
                return Arrays.asList("m1", "m2");
            }

            @Override
            public boolean saveBallot(Ballot ballot) throws use_case.UseCaseDataAccessException {
                return true;
            }

            @Override
            public List<Ballot> getBallots() throws use_case.UseCaseDataAccessException {
                return Arrays.asList();
            }

            @Override
            public int participantsCount() throws use_case.UseCaseDataAccessException {
                return 2;
            }

            @Override
            public boolean isHost() throws use_case.UseCaseDataAccessException {
                return false;
            }
        };

        TestPresenter presenter = new TestPresenter();
        VoteInteractor interactor = new VoteInteractor(throwingGateway, presenter);

        VoteInputData input = new VoteInputData("p1", Arrays.asList("m1", "m2"));
        interactor.submitBallot(input);

        assertNotNull(presenter.lastFailure, "Presenter should receive failure when database exception occurs");
        assertTrue(presenter.lastFailure.contains("Database error"), "Failure message should mention database error");
        assertNull(presenter.lastOutput, "No output should be presented");
    }

}
