package use_case.update_room;

import data_access.note_database.DataAccessException;
import data_access.room.RoomDatabase;
import interface_adapter.shortlist.ShortlistPresenter;
import org.junit.jupiter.api.Test;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRoomTest {
    @Test
    void testUpdateShortlist() {
        RoomDatabase dao1 = new RoomDatabase("User1");
        RoomDatabase dao2 = new RoomDatabase("User2");
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        try {
            dao1.createRoom(roomName);
            dao2.joinRoom(roomName);
            dao1.addMovie("MovieID");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test case: " + e.getMessage());
            e.printStackTrace();
            return;
        }

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistPresenter(null) {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertEquals(1, outputData.getShortlist().size());
                assertEquals("MovieID", outputData.getShortlist().get(0));
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to update: " + message);
            }
        };

        UpdateRoomInputBoundary interactor = new UpdateRoomInteractor(dao2, shortlistOutputBoundary);
        interactor.execute();
    }

    @Test
    void testUpdateWithoutRoom() {
        RoomDatabase dao = new RoomDatabase("User");
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Updated without room.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Room not loaded. Create or join a room first.", message);
            }
        };

        UpdateRoomInputBoundary interactor = new UpdateRoomInteractor(dao, shortlistOutputBoundary);
        interactor.execute();
    }
}