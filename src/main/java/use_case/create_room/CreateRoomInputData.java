package use_case.create_room;

/**
 * Input data for creating a room (e.g., maybe host name?).
 * Input data for creating a room (e.g., maybe host name?).
 */
public class CreateRoomInputData {
    private final String hostName;

    /**
     * Creates an instance of input data for the Create Room use case.
     *
     * @param hostName the name of the user who is creating the room
     */
    public CreateRoomInputData(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the host's name.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }
}
