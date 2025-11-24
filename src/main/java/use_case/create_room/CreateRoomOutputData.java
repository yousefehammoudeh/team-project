package use_case.create_room;

/**
 * Output data after creating a room (room code, host token, etc.).
 */
public class CreateRoomOutputData {
    // Define fields and constructor(s)
    private final String hostName;
    private final String hostId;
    private final String roomCode;
    private final String hostToken;

    public CreateRoomOutputData(String hostName,  String hostId, String roomCode, String hostToken) {
        this.hostName = hostName;
        this.hostId = hostId;
        this.roomCode = roomCode;
        this.hostToken = hostToken;
    }

    public String getRoomCode() { return roomCode; }
    public String getHostToken() { return hostToken; }
    public String getHostName() { return hostName; }
    public String getHostId() { return hostId; }
}

