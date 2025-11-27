package interface_adapter.create_room;

/**
 * Holds UI state for create room flow.
 * Fields to consider:
 * - roomCode
 * - hostToken
 * - error
 */
public class CreateRoomState {
    private String hostName = "";
    private String roomCode = "";
    private String hostToken = "";
    private String error = null;

    public String getHostName() {
        return hostName;
    }

    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    public String getHostToken() {
        return hostToken;
    }

    public void setHostToken(String hostToken) {
        this.hostToken = hostToken;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

