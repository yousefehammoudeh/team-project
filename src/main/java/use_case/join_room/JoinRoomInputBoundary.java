package use_case.join_room;

/**
 * TODO: Interactor API for joining a room.
 */
public interface JoinRoomInputBoundary {
    // TODO: Define method signature(s), e.g., execute(JoinRoomInputData inputData)
    /**
     * Executes the join room use case.
     * @param joinRoomInputData the input data
     */
    void execute(JoinRoomInputData joinRoomInputData);

    /**
     * Executes the switch to login view use case.
     */
    void switchToCreateRoomView();
}