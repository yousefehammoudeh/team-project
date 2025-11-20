package use_case.remove_movie;

import data_access.note_database.DataAccessException;

import java.util.List;

public interface RemoveMovieRoomDataAccessInterface {
    boolean isHost() throws DataAccessException;

    boolean isLocked() throws DataAccessException;

    boolean removeMovie(String movieID) throws DataAccessException;

    List<String> getShortlist() throws DataAccessException;
}
