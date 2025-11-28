package use_case.joined_room;

public interface JoinedRoomInputBoundary {
    /**
     * Executes the joined room use case (leave a room).
     *
     */
    void execute(String roomCode);
}