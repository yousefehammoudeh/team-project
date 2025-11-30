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

}
