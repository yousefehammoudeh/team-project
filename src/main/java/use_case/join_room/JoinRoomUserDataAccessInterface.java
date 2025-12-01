package use_case.join_room;

import java.util.List;

import data_access.note_database.DataAccessException;

/**
 * Interact with the room database to check if a room exists.
 * If the room exists, then save a user to the database.
 */
public interface JoinRoomUserDataAccessInterface {
    /**
      * Checks if the room exists, if it does then save the user.
      * 
      * @param roomCode the room to check existence
      */
    boolean joinRoom(String roomCode) throws DataAccessException;

    /**
     * Set a new participant's name.
     * @param username for a new room database.
    */
    void setUsername(String username);

    /**
     * Return a list of participants in an existing room.
     */
    List<String> getParticipantIDs() throws DataAccessException;

    /**
     * Return the id of the host in an existing room.
     */
    String getHostId() throws DataAccessException;

}