package interface_adapter.created_room;

import java.util.ArrayList;
import java.util.List;

public class CreatedRoomState {
    private String roomCode = "";
    private List<String> participants = new ArrayList<>();

    public String getRoomCode() { return roomCode; }

    public void setRoomCode(String roomCode) { this.roomCode = roomCode; }

    public List<String> getParticipants() { return participants; }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }
}