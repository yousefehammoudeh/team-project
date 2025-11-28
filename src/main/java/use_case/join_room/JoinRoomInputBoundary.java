package use_case.join_room;

public interface JoinRoomInputBoundary {
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