package use_case.join_room;

import java.util.List;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import org.junit.jupiter.api.Test;

import static data_access.HTTPCode.CONFLICT_ERROR;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

class JoinRoomInteractorTest {

    @Test
    void successTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", new java.util.HashMap<>());
        try {
            db.createRoom("c4a760");
        } catch (data_access.note_database.DataAccessException e) {
            fail("Setup failed: " + e.getMessage());
        }

        JoinRoomInputData input = new JoinRoomInputData("Bob", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                assertEquals("c4a760", output.getRoomCode());
                assertEquals(List.of("Alice", "Bob"), output.getParticipants());
                assertEquals("Alice", output.getHostName());
            }

            @Override
            public void presentFailure(String error) {
                fail("Use case failure is unexpected.");
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void noUsernameTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                // should not reach this
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Username cannot be empty", error);
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void emptyRoomTest() {
        // create a room first
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("Alice", "");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Room code cannot be empty", error);
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void wrongRoomTest() {
        // create a room first (no rooms added to simulate wrong room)
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject();

        JoinRoomInputData input = new JoinRoomInputData("Bob", "d4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("Room doesn't exist.", error);
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void multipleParticipantsTest() {
        // create a room first with existing participant Alice
        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", new java.util.HashMap<>());
        try {
            db.createRoom("c4a760");
        } catch (data_access.note_database.DataAccessException e) {
            fail("Setup failed: " + e.getMessage());
        }

        JoinRoomInputData input = new JoinRoomInputData("Alice", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("User already exists.", error);
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(db, successPresenter);
        interactor.execute(input);

    }

    @Test
    void testOtherDataException() {
        // A fake DAO that forces a non-NOT_FOUND exception (catch-else clause)
        JoinRoomUserDataAccessInterface fakeDao = new JoinRoomUserDataAccessInterface() {

            @Override
            public void setUsername(String username) {}

            @Override
            public boolean joinRoom(String roomcode) throws DataAccessException {
                // Force a CONFLICT ERROR (not NOT_FOUND)
                throw new DataAccessException("User or room already exists.", CONFLICT_ERROR);
            }

            @Override
            public List<String> getParticipantIDs() {
                return null;
            }

            @Override
            public String getHostId() {
                return null;
            }
        };

        JoinRoomInputData input = new JoinRoomInputData("Bob", "c4a760");

        JoinRoomOutputBoundary successPresenter = new JoinRoomOutputBoundary() {
            @Override
            public void prepareSuccessView(JoinRoomOutputData output) {
                fail("Use case failure is unexpected.");
            }

            @Override
            public void presentFailure(String error) {
                assertEquals("User or room already exists.", error);
            }

        };

        JoinRoomInputBoundary interactor = new JoinRoomInteractor(fakeDao, successPresenter);
        interactor.execute(input);

    }


}
