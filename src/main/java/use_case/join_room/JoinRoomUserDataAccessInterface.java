package use_case.join_room;

import data_access.note_database.DataAccessException;

import java.util.List;

<<<<<<< HEAD
/**
 * TODO: Gateways for join room flow (fetch room by code, add participant,
 * etc.).
 */
public interface JoinRoomUserDataAccessInterface {
    /**
=======
public interface JoinRoomUserDataAccessInterface {
       /**
>>>>>>> 19e83f2 (S02 use case - fixed the interactor to match room database)
     * Checks if the room exists, if it does then save the user.
     * @param roomCode the room to check existence
     */
    boolean joinRoom(String roomCode) throws DataAccessException;
<<<<<<< HEAD

    void setUsername(String username);

=======
    void setUsername(String username);
>>>>>>> 19e83f2 (S02 use case - fixed the interactor to match room database)
    List<String> getParticipantIDs() throws DataAccessException;

}