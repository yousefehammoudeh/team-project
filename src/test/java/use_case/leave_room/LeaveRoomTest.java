package use_case.leave_room;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class LeaveRoomTest {
    @Test
    void testLeaveRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao1 = new InMemoryRoomDataAccessObject("User1", rooms);
        InMemoryRoomDataAccessObject dao2 = new InMemoryRoomDataAccessObject("User2", rooms);

        try {
            dao1.createRoom("RoomCode");
            dao2.joinRoom("RoomCode");
        } catch (DataAccessException e) {
            fail("Failed to initialize the test");
        }

        LeaveRoomOutputBoundary leaveRoomOutputBoundary = new LeaveRoomOutputBoundary() {
            @Override
            public void present() {
                try {
                    assertEquals(1, dao1.getParticipantIDs().size());
                    assertEquals("User1", dao1.getParticipantIDs().get(0));
                } catch (DataAccessException e) {
                    fail("Failed to test: " + e.getMessage());
                }
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to leave the room: " + message);
            }
        };

        LeaveRoomInputBoundary interactor = new LeaveRoomInteractor(dao2, leaveRoomOutputBoundary);
        interactor.execute();
    }

    @Test
    void testLeaveWithoutRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("User", rooms);

        LeaveRoomOutputBoundary leaveRoomOutputBoundary = new LeaveRoomOutputBoundary() {
            @Override
            public void present() {
                fail("Left a room when there is no room.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Room not loaded. Create or join a room first.", message);
            }
        };

        LeaveRoomInputBoundary interactor = new LeaveRoomInteractor(dao, leaveRoomOutputBoundary);
        interactor.execute();
    }
}
