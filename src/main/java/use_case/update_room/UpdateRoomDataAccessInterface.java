package use_case.update_room;

import data_access.note_database.DataAccessException;

import java.util.List;

public interface UpdateRoomDataAccessInterface {
    public void refreshRoom() throws DataAccessException;
    public List<String> getShortlist() throws DataAccessException;
    public boolean isLocked() throws DataAccessException;
}
