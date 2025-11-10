package data_access.room;

import entity.Room;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Simple in-memory store for active rooms and their participants.
 * - Maintain mapping: roomCode -> Room aggregate
 * - Provide helpers to add/remove participants, ballots, filters, etc.
 * - This store backs InMemoryRoomDataAccessObject; swap with persistent version later.
 */
public class ActiveRoomStore {

    // TODO: Replace with thread-safe collection if needed
    private final Map<String, Room> roomsByCode = new HashMap<>();

    // TODO: Add CRUD methods for rooms, participants, shortlist, ballots, filters
}

