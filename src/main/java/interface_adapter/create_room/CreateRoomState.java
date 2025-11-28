package interface_adapter.create_room;

/**
 * Holds UI state for create room flow.
 * Fields to consider:
 * - roomCode
 * - error
 */
public class CreateRoomState {
    private String hostName = "";
    private String roomCode = "";
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

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
