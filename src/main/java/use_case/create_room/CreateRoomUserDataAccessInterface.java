package use_case.create_room;

import entity.Room;

/**
 * Gateways to persist/fetch room for create flow.
 */
public interface CreateRoomUserDataAccessInterface {
    // Methods to save new room, ensure code uniqueness, generate tokens, etc.
    boolean existsByRoomCode(String roomCode);

    //
    void save(Room room);

    void setCurrentRoom(String roomName);

    //
    boolean verifyRoomUniquenessPerUser(String hostId);
}
