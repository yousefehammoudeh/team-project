package use_case.join_room;
import entity.Room;

/**
 * TODO: Output data after joining (room summary/current state).
 */
public class JoinRoomOutputData {
    // TODO: Define fields and constructor(s)
    //private final String username;
    private final Room room;

    public JoinRoomOutputData(Room room) {
        //this.username = username;
        this.room = room;
    }

//    public String getUsername() {
//        return username;
//    }

    public Room getRoom() { return room; }
}

