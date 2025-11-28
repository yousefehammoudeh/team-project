package use_case.create_room;

/**
 * Output data after creating a room (room code, host name).
 */
public class CreateRoomOutputData {
    private final String hostName;
    private final String roomCode;

    public CreateRoomOutputData(String hostName, String roomCode) {
        this.hostName = hostName;
        this.roomCode = roomCode;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public String getHostName() {
        return hostName;
    }
}
