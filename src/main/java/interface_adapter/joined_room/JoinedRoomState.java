package interface_adapter.joined_room;

import entity.Participant;
import entity.Room;

import java.util.ArrayList;
import java.util.List;

public class JoinedRoomState {
    private List<String> participants = new ArrayList<>();
    private String roomcode = "";
    private String currentUser = "";


    public JoinedRoomState(JoinedRoomState copy) {
        participants = copy.participants;
        roomcode = copy.roomcode;
        currentUser = copy.currentUser;
    }

    public JoinedRoomState() {}

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants;
    }

    public void setRoomcode(String password) {
        this.roomcode = password;
    }

    public String getRoomcode() {
        return this.roomcode;
    }

    public void setCurrentUser(String currentUser) { this.currentUser = currentUser;}

    public String getCurrentUser() { return this.currentUser;}


}