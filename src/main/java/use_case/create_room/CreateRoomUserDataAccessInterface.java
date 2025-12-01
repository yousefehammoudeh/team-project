package use_case.create_room;

import data_access.note_database.DataAccessException;

/**
 * Gateways to persist/fetch room for create flow.
 */
public interface CreateRoomUserDataAccessInterface {

    /**
     * Persists a new room identified by the given room code.
     *
     * @param roomCode the unique code of the newly created room
     * @throws DataAccessException if a storage-related error occurs
     */
    void createRoom(String roomCode) throws DataAccessException;

    /**
     * Returns the username associated with the current host.
     *
     * @return the host's username
     */
    String getUsername();

    /**
     * Sets the username for the current host.
     *
     * @param username the host's username
     */
    void setUsername(String username);
}