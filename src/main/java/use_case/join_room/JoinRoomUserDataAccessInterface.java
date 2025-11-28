package use_case.join_room;

import data_access.note_database.DataAccessException;
import entity.Participant;
import entity.Room;

import java.util.List;

/**
 * TODO: Gateways for join room flow (fetch room by code, add participant,
 * etc.).
 */
public interface JoinRoomUserDataAccessInterface {
    // TODO: Define data access methods
    /**
     * Checks if the given username exists.
     * 
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */

    /**
     * Checks if the room exists, if it does then save the user.
     * 
     * @param roomCode the room to check existence
     */
    boolean joinRoom(String roomCode) throws DataAccessException;

    void setUsername(String username);

    List<String> getParticipantIDs() throws DataAccessException;

}