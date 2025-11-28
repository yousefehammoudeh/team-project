package use_case.toggle_lock_room;

import data_access.note_database.DataAccessException;

import java.util.List;

public interface ToggleLockRoomDataAccessInterface {
    boolean isHost() throws DataAccessException;

    boolean isLocked() throws DataAccessException;

    void setLocked(boolean locked) throws DataAccessException;

    List<String> getShortlist() throws DataAccessException;
}
