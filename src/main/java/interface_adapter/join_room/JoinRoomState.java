package interface_adapter.join_room;

/**
 * TODO: Holds UI state for join room flow.
 * Fields to consider:
 * - roomCode
 * - participantName
 * - currentRoomState summary
 * - error
 */
public class JoinRoomState {
    // TODO: Define fields and accessors
    private String username = "";
    private String usernameError;
    private String roomcode = "";
    private String roomcodeError;

    public String getUsername() {
        return username;
    }

    public String getUsernameError() {
        return usernameError;
    }

    public String getRoomcode() {
        return roomcode;
    }

    public String getRoomcodeError() {
        return roomcodeError;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setUsernameError(String usernameError) {
        this.usernameError = usernameError;
    }

    public void setRoomcode(String roomcode) {
        this.roomcode = roomcode;
    }

    public void setRoomcodeError(String roomcodeError) {
        this.roomcodeError = roomcodeError;
    }

    @Override
    public String toString() {
        return "JoinRoomState{"
                + "username='" + username + '\''
                + ", roomcode='" + roomcode + '\''
                + '}';
    }
}

