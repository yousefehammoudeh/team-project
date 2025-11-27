package use_case.joined_room;

import use_case.join_room.JoinRoomInputData;

public interface JoinedRoomInputBoundary {
    /**
     * Executes the joined room use case (leave a room).
     *
     */
    void execute(String roomCode);
}