package use_case.create_room;

import entity.Room;

import java.util.UUID;

/**
 * TODO: Gateways to persist/fetch room for create flow.
 */
public interface CreateRoomUserDataAccessInterface {
    // TODO: Methods to save new room, ensure code uniqueness, generate tokens, etc.
    boolean existsByRoomCode(String room_code);

    void save(Room room);

    Room get(String room_code);

    void setCurrentRoom(String room_name);

    String getCurrentRoomName();

    boolean verifyRoomUniquenessPerUser(String hostId);
}

