package data_access.room;

import entity.Room;
import entity.Participant;
import use_case.join_room.JoinRoomUserDataAccessInterface;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Simple in-memory store for active rooms and their participants.
 * - Maintain mapping: roomCode -> Room aggregate
 * - Provide helpers to add/remove participants, ballots, filters, etc.
 * - This store backs InMemoryRoomDataAccessObject; swap with persistent version later.
 */
public class ActiveRoomStore implements JoinRoomUserDataAccessInterface {

    // TODO: Replace with thread-safe collection if needed
    private final Map<String, Room> roomsByCode = new HashMap<>();
    private final Map<String, Room> usernames = new HashMap<>();

    // TODO: Add CRUD methods for rooms, participants, shortlist, ballots, filters

    @Override
    public boolean existsByName(String identifier) {
        return usernames.containsKey(identifier);
    }

    public boolean existsByCode(String identifier) {
        return roomsByCode.containsKey(identifier);
    }

    //this doesnt save users between program runs, closing the program automatically resets users
    @Override
    public void saveUser(Participant user, Room room) {
        usernames.put(user.getName(), room);
        room.addParticipant(user);
    }

    @Override
    public Room get(String roomCode) {
        return roomsByCode.get(roomCode);
    }
}

