package interface_adapter.created_room;

import java.util.ArrayList;
import java.util.List;

public class CreatedRoomState {
    private String roomCode = "";
    private String hostName = "";
    private List<String> participants = new ArrayList<>();
    private String error;

    public String getHostName() { return hostName; }

    public void setHostName(String hostName) { this.hostName = hostName; }

    public String getRoomCode() { return roomCode; }

    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public List<String> getParticipants() { return participants; }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}