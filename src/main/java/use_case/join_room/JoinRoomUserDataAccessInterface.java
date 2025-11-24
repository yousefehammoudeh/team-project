package use_case.join_room;

import data_access.note_database.DataAccessException;

import java.util.List;

/**
 * TODO: Gateways for join room flow (fetch room by code, add participant,
 * etc.).
 */
public interface JoinRoomUserDataAccessInterface {
    /**
     * Checks if the room exists, if it does then save the user.
     * @param roomCode the room to check existence
     */
    boolean joinRoom(String roomCode) throws DataAccessException;

    void setUsername(String username);

    List<String> getParticipantIDs() throws DataAccessException;

}