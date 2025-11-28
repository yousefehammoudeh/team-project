package use_case.join_room;

public class JoinRoomInputData {
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