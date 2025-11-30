package use_case.update_room;

import data_access.note_database.DataAccessException;
import interface_adapter.shortlist.ShortlistPresenter;
import org.junit.jupiter.api.Test;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import static org.junit.jupiter.api.Assertions.*;

class UpdateRoomTest {
    @Test
    void testUpdateShortlist() {
        java.util.Map<String, entity.Room> sharedRooms = new java.util.HashMap<>();
        data_access.room.InMemoryRoomDataAccessObject dao1 = new data_access.room.InMemoryRoomDataAccessObject("User1",
                sharedRooms);
        data_access.room.InMemoryRoomDataAccessObject dao2 = new data_access.room.InMemoryRoomDataAccessObject("User2",
                sharedRooms);
        String roomName = "testRoom_" + System.currentTimeMillis();
        try {
            dao1.createRoom(roomName);
            dao2.joinRoom(roomName);
            dao1.addMovie("MovieID");
        } catch (DataAccessException e) {
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
    @org.junit.jupiter.api.Disabled("Rate limiting test not applicable to in-memory DAO")
    void testRateLimitCooldown() {
        data_access.room.InMemoryRoomDataAccessObject dao = new data_access.room.InMemoryRoomDataAccessObject("User",
                new java.util.HashMap<>());
        String roomName = "testRoom_" + System.currentTimeMillis();
        try {
            dao.createRoom(roomName);
        } catch (DataAccessException e) {
            fail("Failed to initialize the test case: " + e.getMessage());
            return;
        }

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistPresenter(null) {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Made an update when the API is rate limited.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Too many requests. Next update will take place after 20 seconds", message);
            }
        };

        // Sorry API
        boolean rateLimited = false;
        while (!rateLimited) {
            try {
                dao.addMovie("MovieID");
            } catch (DataAccessException e) {
                rateLimited = true;
            }
        }
        UpdateRoomInputBoundary interactor = new UpdateRoomInteractor(dao, shortlistOutputBoundary);
        interactor.execute();

        final boolean[] presenterCalled = { false };
        ShortlistOutputBoundary shortlistOutputBoundaryInCooldown = new ShortlistPresenter(null) {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Made an update in cooldown.");
            }

            @Override
            public void presentFailure(String message) {
                if (!presenterCalled[0]) {
                    presenterCalled[0] = true;
                } else {
                    fail("Made an update in cooldown.");
                }
            }
        };
        UpdateRoomInputBoundary interactorInCooldown = new UpdateRoomInteractor(dao, shortlistOutputBoundaryInCooldown);
        interactorInCooldown.execute();
        interactorInCooldown.execute();

        // must be an 1-element array to use in the output boundary
        presenterCalled[0] = false;
        ShortlistOutputBoundary shortlistOutputBoundaryAfterCooldown = new ShortlistPresenter(null) {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Made an update in cooldown.");
            }

            @Override
            public void presentFailure(String message) {
                if (!presenterCalled[0]) {
                    presenterCalled[0] = true;
                } else {
                    assertEquals("Too many requests. Next update will take place after 20 seconds", message);
                }
            }
        };
        UpdateRoomInputBoundary interactorAfterCooldown = new UpdateRoomInteractor(dao,
                shortlistOutputBoundaryAfterCooldown);
        interactorAfterCooldown.execute();
        try {
            Thread.sleep(21000);
        } catch (InterruptedException e) {
            fail("Interrupted while waiting for cooldown.");
            e.printStackTrace();
        }
        interactorAfterCooldown.execute();
    }

    @Test
    void testUpdateWithoutRoom() {
        data_access.room.InMemoryRoomDataAccessObject dao = new data_access.room.InMemoryRoomDataAccessObject("User",
                new java.util.HashMap<>());

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
