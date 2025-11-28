package use_case.create_room;

import data_access.note_database.DataAccessException;

/**
 * Gateways to persist/fetch room for create flow.
 */
public interface CreateRoomUserDataAccessInterface {
    void createRoom(String roomCode) throws DataAccessException;

    String getUsername();
}