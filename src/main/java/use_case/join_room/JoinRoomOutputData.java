
package use_case.join_room;

import java.util.List;


public class JoinRoomOutputData {
    //private final String username;
    private final List<String> participants;
    private final String code;
    private final String currentUser;
    public JoinRoomOutputData(List<String> p, String name, String roomCode) {

        this.participants = p;
        this.code = roomCode;
        this.currentUser = name;
    }

    public String getRoomCode() {
        return code;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getCurrentUser() { return currentUser; }

}
