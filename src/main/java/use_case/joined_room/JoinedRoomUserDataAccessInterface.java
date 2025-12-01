
package use_case.joined_room;

import data_access.note_database.DataAccessException;

public interface JoinedRoomUserDataAccessInterface {

    void leaveRoom(String roomCode) throws DataAccessException;

    void setUsername(String username);
}

