package interface_adapter.created_room;

import java.util.ArrayList;
import java.util.List;

public class CreatedRoomState {
    private String roomCode = "";
    private String hostName = "";
    private List<String> participants = new ArrayList<>();
    private String error;

    /**
     * Returns the host's name.
     *
     * @return the host name
     */
    public String getHostName() {
        return hostName;
    }

    /**
     * Sets the host's name.
     *
     * @param hostName the host name
     */
    public void setHostName(String hostName) {
        this.hostName = hostName;
    }

    /**
     * Returns the room code.
     *
     * @return the room code
     */
    public String getRoomCode() {
        return roomCode;
    }

    /**
     * Sets the room code.
     *
     * @param roomCode the unique room code
     */
    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    /**
     * Returns the list of participants currently in the room.
     *
     * @return the list of participants
     */
    public List<String> getParticipants() {
        return participants;
    }

    /**
     * Sets the list of participants in the room.
     *
     * @param participants a list of participant names
     */
    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    /**
     * Returns the error message, if any.
     *
     * @return the error message, or {@code null} if no error is present
     */
    public String getError() {
        return error;
    }

    /**
     * Sets an error message to be displayed on the UI.
     *
     * @param error the error message (nullable)
     */
    public void setError(String error) {
        this.error = error;
    }
}