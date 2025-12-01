package use_case.create_room;

/**
 * Output data after creating a room (room code, host name).
 */
public class CreateRoomOutputData {
    private final String hostName;
    private final String roomCode;

    /**
     * Constructs the output data for a successful create-room use case.
     *
     * @param hostName for host name.
     * @param roomCode for unique room code.
     */
    public CreateRoomOutputData(String hostName, String roomCode) {
        this.hostName = hostName;
        this.roomCode = roomCode;
    }

    /**
     * Returns the generated room code.
     *
     * @return the room's unique code
     */
    public String getRoomCode() {
        return roomCode;
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
