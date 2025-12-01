package use_case.joined_room;
import java.util.List;

public class JoinedRoomOutputData {
    //private final String username;

    private final String code;

    public JoinedRoomOutputData(String roomCode) {
        this.code = roomCode;

    }

    public String getRoomCode() {
        return code;
    }
}