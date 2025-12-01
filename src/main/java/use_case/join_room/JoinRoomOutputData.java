
package use_case.join_room;

import java.util.List;


public class JoinRoomOutputData {
    //private final String username;
    private final List<String> participants;
    private final String code;
    private final String hostName;
    public JoinRoomOutputData(List<String> p, String hostName, String roomCode) {

        this.participants = p;
        this.code = roomCode;
        this.hostName = hostName;
    }

    public String getRoomCode() {
        return code;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public String getHostName() { return hostName; }

}
