package use_case.create_room;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomInteractorTest {

    @Test
    void successTest() {
        Map<String, Room> storage = new HashMap<>();
        CreateRoomUserDataAccessInterface db = new InMemoryRoomDataAccessObject("Alice", storage);

        CreateRoomInputData input = new CreateRoomInputData("Alice");

        CreateRoomOutputBoundary presenter = new CreateRoomOutputBoundary() {

            @Override
            public void present(CreateRoomOutputData output) {
                assertEquals("Alice", output.getHostName());
                assertNotNull(output.getRoomCode());
                assertEquals(6, output.getRoomCode().length(),
                        "Room code must be 6 characters");

                assertTrue(storage.containsKey(output.getRoomCode()),
                        "DAO should contain created room");

                Room room = storage.get(output.getRoomCode());

                assertEquals("Alice", room.getHostId());
                assertEquals(1, room.getParticipants().size());
                assertEquals("Alice", room.getParticipants().get(0).getId());
            }

            @Override
            public void presentFailure(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        CreateRoomInputBoundary interactor = new CreateRoomInteractor(db, presenter);
        interactor.execute(input);
    }

    @Test
    void roomCodeCollisionTest() {

        Map<String, Room> storage = new HashMap<>();

        storage.put("ABC123", new Room("ABC123", "Alice"));

        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", storage);

        CreateRoomInputData input = new CreateRoomInputData("Alice");

        CreateRoomOutputBoundary presenter = new CreateRoomOutputBoundary() {

            @Override
            public void present(CreateRoomOutputData output) {
                assertNotEquals("ABC123", output.getRoomCode(),
                        "Interactor must retry on room code conflict");
                assertEquals("Alice", output.getHostName());
            }

            @Override
            public void presentFailure(String error) {
                fail("Unexpected failure: " + error);
            }
        };

        CreateRoomInputBoundary interactor = new CreateRoomInteractor(db, presenter);
        interactor.execute(input);
    }

    @Test
    void daoThrowsUnexpectedErrorTest() {

        InMemoryRoomDataAccessObject db = new InMemoryRoomDataAccessObject("Alice", new HashMap<>()) {

            @Override
            public void createRoom(String roomCode) throws DataAccessException {
                throw new DataAccessException("Database exploded", 500);
            }
        };

        CreateRoomInputData input = new CreateRoomInputData("Alice");

        CreateRoomOutputBoundary presenter = new CreateRoomOutputBoundary() {
            @Override
            public void present(CreateRoomOutputData output) {
                fail("Expected failure, not success.");
            }

            @Override
            public void presentFailure(String error) {
                assertTrue(error.contains("Database exploded"));
            }
        };

        CreateRoomInputBoundary interactor = new CreateRoomInteractor(db, presenter);
        interactor.execute(input);
    }
}