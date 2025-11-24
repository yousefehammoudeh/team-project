package use_case.join_room;
import entity.Room;

import java.util.List;

/**
 * TODO: Output data after joining (room summary/current state).
 */
public class JoinRoomOutputData {
    // TODO: Define fields and constructor(s)
    //private final String username;
    private final List<String> participants;
    private final String code;
    public JoinRoomOutputData(List<String> p, String roomCode) {

        this.participants = p;
        this.code = roomCode;
    }

    public String getRoomCode() {
        return code;
    }

    public List<String> getParticipants() {
        return participants;
    }

}

