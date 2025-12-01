package use_case.leave_room;

import data_access.note_database.DataAccessException;

public interface LeaveRoomDataAccessInterface {
    void leaveRoom() throws DataAccessException;
}
