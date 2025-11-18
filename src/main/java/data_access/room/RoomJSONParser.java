package data_access.room;

import entity.Ballot;
import entity.Participant;
import entity.Room;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * TODO: Simple in-memory store for active rooms and their participants.
 * - Maintain mapping: roomCode -> Room aggregate
 * - Provide helpers to add/remove participants, ballots, filters, etc.
 * - This store backs InMemoryRoomDataAccessObject; swap with persistent version later.
 */
public class RoomJSONParser {
    private static final String CODE = "code";
    private static final String HOST = "host";
    private static final String LOCKED = "locked";
    private static final String PARTICIPANTS = "participants";
    private static final String SHORTLIST = "shortlist";
    private static final String BALLOTS = "ballots";
    private static final String NAME = "name";
    private static final String BALLOT = "ballot";

    public static Room JSONToRoom(String json) {
        JSONObject jsonObject = new JSONObject(json);
        String code = jsonObject.getString(CODE);
        String hostId = jsonObject.getString(HOST);
        boolean locked = jsonObject.getBoolean(LOCKED);

        JSONArray participantsArray = jsonObject.getJSONArray(PARTICIPANTS);
        List<Participant> participants = new ArrayList<>();
        for (int i = 0; i < participantsArray.length(); i++) {
            String name = participantsArray.getString(i);
            participants.add(new Participant(name, name));
        }

        JSONArray shortlistArray = jsonObject.getJSONArray(SHORTLIST);
        List<String> shortlist = new ArrayList<>();
        for (int i = 0; i < shortlistArray.length(); i++) {
            shortlist.add(shortlistArray.getString(i));
        }

        JSONArray ballotArray = jsonObject.getJSONArray(BALLOTS);
        List<Ballot> ballots = new ArrayList<>();
        for (int i = 0; i < ballotArray.length(); i++) {
            JSONObject ballotObject = ballotArray.getJSONObject(i);
            String participantId = ballotObject.getString(NAME);

            JSONArray movieIdArray = ballotObject.getJSONArray(BALLOT);
            List<String> movieIds = new ArrayList<>();
            for (int j = 0; j < movieIdArray.length(); j++) {
                movieIds.add(movieIdArray.getString(j));
            }

            ballots.add(new Ballot(participantId, movieIds));
        }

        return new Room(code, hostId, locked, participants, shortlist, ballots);
    }
    public static String RoomToJSON(Room room) {
        JSONObject jsonObject = new JSONObject();
        jsonObject.put(CODE, room.getCode());
        jsonObject.put(HOST, room.getHostId());
        jsonObject.put(LOCKED, room.isLocked());

        JSONArray participantsArray = new JSONArray();
        for (Participant participant : room.getParticipants()) {
            participantsArray.put(participant.getId());
        }
        jsonObject.put(PARTICIPANTS, participantsArray);

        JSONArray shortlistArray = new JSONArray();
        for (String movieId : room.getShortlist()) {
            shortlistArray.put(movieId);
        }
        jsonObject.put(SHORTLIST, shortlistArray);

        JSONArray ballotArray = new JSONArray();
        for (Ballot ballot : room.getBallots()) {
            JSONObject ballotObject = new JSONObject();
            ballotObject.put(NAME, ballot.getParticipantId());

            JSONArray movieIdArray = new JSONArray();
            for (String movieId : ballot.getRankedMovieIds()) {
                movieIdArray.put(movieId);
            }
            ballotObject.put(BALLOT, movieIdArray);

            ballotArray.put(ballotObject);
        }
        jsonObject.put(BALLOTS, ballotArray);

        return jsonObject.toString();
    }
}

