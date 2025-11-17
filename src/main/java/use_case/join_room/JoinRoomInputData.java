package use_case.join_room;

/**
 * TODO: Input data for join room (code + name).
 */
public class JoinRoomInputData {
    // TODO: Define fields and constructor(s)
    private final String username;
    private final String roomcode;

    public JoinRoomInputData(String username, String roomcode) {
        this.username = username;
        this.roomcode = roomcode;
    }

    String getUsername() {
        return username;
    }

    String getRoomcode() {
        return roomcode;
    }
}

