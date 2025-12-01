package use_case.joined_room;

public interface JoinedRoomOutputBoundary {
    /**
     * Prepares the success view for the Joined Room Case.
     * @param outputData the output data
     */
    void prepareSuccessView(JoinedRoomOutputData outputData);

    /**
     * Prepares the failure view for the Signup Use Case.
     * @param message the explanation of the failure
     */
    void presentFailure(String message);
}