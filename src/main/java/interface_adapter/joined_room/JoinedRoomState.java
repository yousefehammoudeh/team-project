package interface_adapter.joined_room;

import entity.Participant;
import entity.Room;
import java.util.List;

public class JoinedRoomState {
    private List<String> participants;
    private String roomcode = "";


    public JoinedRoomState(JoinedRoomState copy) {
        participants = copy.participants;
        roomcode = copy.roomcode;
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
        return roomcode;
    }


}
