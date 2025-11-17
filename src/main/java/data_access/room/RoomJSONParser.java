package data_access.room;

import entity.Room;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * TODO: Simple in-memory store for active rooms and their participants.
 * - Maintain mapping: roomCode -> Room aggregate
 * - Provide helpers to add/remove participants, ballots, filters, etc.
 * - This store backs InMemoryRoomDataAccessObject; swap with persistent version later.
 */
public class RoomJSONParser {
    public static Room JSONToRoom(String json) throws JSONException {
        return null;
    }
    public static JSONObject RoomToJSON(Room room) throws JSONException {
        return null;
    }
}

