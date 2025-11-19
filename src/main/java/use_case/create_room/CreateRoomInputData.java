package use_case.create_room;

/**
 * Input data for creating a room (e.g., maybe host name?).
 * Input data for creating a room (e.g., maybe host name?).
 */
public class CreateRoomInputData {
    // Define fields and constructor(s)
    private final String hostName;
    private final String hostId;

    public CreateRoomInputData(String hostName, String hostId) {
        this.hostName = hostName;
        this.hostId = hostId;
    }

    public String getHostName() {
        return hostName;
    }

    public String getHostId() {
        return hostId;
    }
}
