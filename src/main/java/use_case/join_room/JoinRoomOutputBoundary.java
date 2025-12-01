package use_case.join_room;

public interface JoinRoomOutputBoundary {
    /**
     * Prepares the success view for the Join Room Case.
     * @param outputData the output data
     */
    void prepareSuccessView(JoinRoomOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param message the explanation of the failure
     */
    void presentFailure(String message);
}