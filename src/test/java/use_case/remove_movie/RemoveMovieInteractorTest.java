package use_case.remove_movie;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import org.junit.jupiter.api.Test;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class RemoveMovieInteractorTest {
    @Test
    void testRemoveMovie() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.addMovie("Movie1");
            dao.addMovie("Movie2");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }

        RemoveMovieInputData inputData = new RemoveMovieInputData("Movie2");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertEquals(1, outputData.getShortlist().size());
                assertEquals("Movie1", outputData.getShortlist().get(0));
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to remove a movie from shortlist: " + message);
            }
        };

        RemoveMovieInputBoundary interactor = new RemoveMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testRemoveNonexistentMovie() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.addMovie("SomeMovie");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }

        RemoveMovieInputData inputData = new RemoveMovieInputData("OtherMovie");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Removed a movie that does not exist in the shortlist.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("The movie is not in the shortlist.", message);
            }
        };

        RemoveMovieInputBoundary interactor = new RemoveMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testRemoveMovieWhenLocked() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.addMovie("MovieID");
            dao.setLocked(true);
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }

        RemoveMovieInputData inputData = new RemoveMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Removed a movie when the room is locked.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("The room is locked.", message);
            }
        };

        RemoveMovieInputBoundary interactor = new RemoveMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testRemoveMovieWithoutRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);

        RemoveMovieInputData inputData = new RemoveMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Added a movie without room.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Room not loaded. Create or join a room first.", message);
            }
        };

        RemoveMovieInputBoundary interactor = new RemoveMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }
}
