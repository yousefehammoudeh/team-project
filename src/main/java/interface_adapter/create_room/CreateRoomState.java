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

    /**
     * Returns the host name currently stored in this state.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the host name displayed or processed by the UI.
     *
     * @param hostName the host name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the room code generated for the newly created room.
     *
     * @return the room code
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * Sets the room code to be shown on the UI.
     *
     * @param roomCode the generated room code
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Returns the error message, if any.
     *
     * @return the error message or {@code null} if there is no error
     */
    public String getError() {
        return error;
    }

    /**
     * Sets the error message to be displayed on the UI.
     *
     * @param error the error message (nullable)
     */
    public void setError(String error) {
        this.error = error;
    }
}
