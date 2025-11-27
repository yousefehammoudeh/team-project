package use_case.remove_movie;

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

public class RemoveMovieInteractorTest {
    @Test
    void testRemoveMovie() throws DataAccessException {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        dao.createRoom("RoomCode");
        dao.addMovie("Movie1");
        dao.addMovie("Movie2");

        RemoveMovieInputData inputData = new RemoveMovieInputData("Movie2");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistPresenter(null) {
            @Override
            public void present(ShortlistOutputData outputData) {
                assertEquals(1, outputData.getShortlist().size());
                assertEquals("Movie1", outputData.getShortlist().get(0));
            }

            @Override
            public void presentFailure(String message) {
                fail("Failed to add a movie to an empty shortlist: " + message);
            }
        };

        RemoveMovieInputBoundary interactor = new RemoveMovieInteractor(dao, shortlistOutputBoundary);
        interactor.execute(inputData);
    }

    @Test
    void testRemoveNonexistentMovie() throws DataAccessException {
        Map<String, Room> rooms = new HashMap<>();
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject("Username", rooms);
        dao.createRoom("RoomCode");
        dao.addMovie("SomeMovie");

        RemoveMovieInputData inputData = new RemoveMovieInputData("OtherMovie");

        ShortlistOutputBoundary shortlistOutputBoundary = new ShortlistPresenter(null) {
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
}
