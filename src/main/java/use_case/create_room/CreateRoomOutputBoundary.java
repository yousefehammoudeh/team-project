package use_case.create_room;

/**
 * Presenter API for create room success/failure.
 */
public interface CreateRoomOutputBoundary {

    /**
     * Handles the successful creation of a room.
     *
     * @param outputData the data representing the result of the room creation
     */
    void present(CreateRoomOutputData outputData);

    /**
     * Handles a failure that occurred during the room creation process.
     *
     * @param message a description of the failure
     */
    void presentFailure(String message);
}
