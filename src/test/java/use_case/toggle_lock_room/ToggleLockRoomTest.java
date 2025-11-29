package use_case.toggle_lock_room;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import org.junit.jupiter.api.Test;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInputData;
import use_case.add_movie.AddMovieInteractor;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class ToggleLockRoomTest {
    @Test
    void testLockRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
        }

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertTrue(outputData.isLocked());
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to lock the room.");
            }
        };

        ToggleLockRoomInputBoundary interactor = new ToggleLockRoomInteractor(dao, shortlistOutputBoundary);
        interactor.execute();
    }

    @Test
    void testUnlockRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.setLocked(true);
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
        }

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertFalse(outputData.isLocked());
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to unlock the room.");
            }
        };

        ToggleLockRoomInputBoundary interactor = new ToggleLockRoomInteractor(dao, shortlistOutputBoundary);
        interactor.execute();
    }

    @Test
    void testLockRoomNotHost() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao1 = new InMemoryRoomDataAccessObject("user1", rooms);
        InMemoryRoomDataAccessObject dao2 = new InMemoryRoomDataAccessObject("user2", rooms);
        try {
            dao1.createRoom("RoomCode");
            dao2.joinRoom("RoomCode");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
        }

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("The room was locked by a non-host participant.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Only the host can lock the room.", message);
            }
        };

        ToggleLockRoomInputBoundary interactor = new ToggleLockRoomInteractor(dao2, shortlistOutputBoundary);
        interactor.execute();
    }

    @Test
    void testLockWithoutRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Locked when there is no room.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Room not loaded. Create or join a room first.", message);
            }
        };

        ToggleLockRoomInputBoundary interactor = new ToggleLockRoomInteractor(dao, shortlistOutputBoundary);
        interactor.execute();
    }
}
