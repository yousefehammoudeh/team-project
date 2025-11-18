package use_case.add_movie;

import data_access.note_database.DataAccessException;

import java.util.List;

public interface AddMovieRoomDataAccessInterface {
    boolean isHost() throws DataAccessException;

    boolean isLocked() throws DataAccessException;

    boolean addMovie(String movieID) throws DataAccessException;

    List<String> getMovieIDs() throws DataAccessException;
}
