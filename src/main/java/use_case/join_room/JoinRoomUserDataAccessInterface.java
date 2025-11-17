package use_case.join_room;

import entity.Participant;
import entity.Room;

/**
 * TODO: Gateways for join room flow (fetch room by code, add participant, etc.).
 */
public interface JoinRoomUserDataAccessInterface {
    // TODO: Define data access methods
    /**
     * Checks if the given username exists.
     * @param username the username to look for
     * @return true if a user with the given username exists; false otherwise
     */
    boolean existsByName(String username);

    boolean existsByCode(String roomCode);

    Room get(String roomCode);

    /**
     * Saves the user.
     * @param user the user to save
     */
    void saveUser(Participant user, Room room);
}

