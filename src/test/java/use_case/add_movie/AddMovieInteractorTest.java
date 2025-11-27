package use_case.add_movie;

import data_access.note_database.DataAccessException;
import data_access.room.InMemoryRoomDataAccessObject;
import entity.Room;
import interface_adapter.shortlist.ShortlistPresenter;
import org.junit.jupiter.api.Test;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

class AddMovieInteractorTest {
    @Test
    void testAddMovie() throws DataAccessException {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        dao.createRoom("RoomCode");

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
    void testAddManyMovies() throws DataAccessException {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        dao.createRoom("RoomCode");
        dao.addMovie("Movie1");
        dao.addMovie("Movie2");

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
    void testAddExistingMovie() throws DataAccessException {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        dao.createRoom("RoomCode");
        dao.addMovie("MovieID");

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
}
