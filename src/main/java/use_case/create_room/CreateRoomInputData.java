package use_case.create_room;

/**
 * Input data for creating a room (e.g., maybe host name?).
 * Input data for creating a room (e.g., maybe host name?).
 */
public class CreateRoomInputData {
    // Define fields and constructor(s)
    private final String hostName;

    public CreateRoomInputData(String hostName) {
        this.hostName = hostName;
    }

    public String getHostName() {
        return hostName;
    }
}
