package use_case.add_movie;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import org.junit.jupiter.api.Test;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddMovieInteractorTest {
    @Test
    void testAddMovie() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }


        AddMovieInputData inputData = new AddMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertEquals("MovieID", outputData.getShortlist().get(0));
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to add a movie to an empty shortlist: " + message);
            }
        };

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testAddManyMovies() {
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

        AddMovieInputData inputData = new AddMovieInputData("Movie3");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertEquals(3, outputData.getShortlist().size());
                assertTrue(outputData.getShortlist().contains("Movie1"));
                assertTrue(outputData.getShortlist().contains("Movie2"));
                assertTrue(outputData.getShortlist().contains("Movie3"));
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to add a movie to an empty shortlist: " + message);
            }
        };

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testAddExistingMovie() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.addMovie("MovieID");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }

        AddMovieInputData inputData = new AddMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Added a movie that already exists in the shortlist.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("The movie already exists.", message);
            }
        };

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testAddMovieWhenLocked() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        try {
            dao.createRoom("RoomCode");
            dao.setLocked(true);
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
            e.printStackTrace();
        }

        AddMovieInputData inputData = new AddMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Added a movie when the room is locked.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("The room is locked.", message);
            }
        };

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testAddMovieWithoutRoom() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);

        AddMovieInputData inputData = new AddMovieInputData("MovieID");

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

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testAddMovieNotHost() {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao1 = new InMemoryRoomDataAccessObject("Username1", rooms);
        InMemoryRoomDataAccessObject dao2 = new InMemoryRoomDataAccessObject("Username2", rooms);
        try {
            dao1.createRoom("RoomCode");
            dao2.joinRoom("RoomCode");
            dao1.addMovie("MovieID");
        }
        catch (DataAccessException e) {
            fail("Failed to initialize the test.");
        }

        AddMovieInputData inputData = new AddMovieInputData("MovieID");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistOutputBoundary() {
            @Override
            public void present(ShortlistOutputData outputData) {
                fail("Added a movie as non-host.");
            }

            @Override
            public void presentFailure(String message) {
                assertEquals("Only the host can add movies to the shortlist.", message);
            }
        };

        AddMovieInputBoundary interactor = new AddMovieInteractor(dao2, shortlistOutputBoundary);
        interactor.execute(inputData);
    }
}
