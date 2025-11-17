package use_case.joined_room;

import use_case.join_room.JoinRoomInputData;

public interface JoinedRoomInputBoundary {
    /**
     * Executes the join room use case.
     * @param joinedRoomInputData the input data
     */
    void execute(JoinedRoomInputData joinedRoomInputData);
}
