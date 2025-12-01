package use_case.joined_room;

public class JoinedRoomInputData {
    private final String roomcode;

    public JoinedRoomInputData(String roomcode) {
        this.roomcode = roomcode;
    }

    String getRoomcode() {
        return roomcode;
    }
}