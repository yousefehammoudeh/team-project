package use_case.create_room;

/**
 * Presenter API for create room success/failure.
 */
public interface CreateRoomOutputBoundary {
    void present(CreateRoomOutputData outputData);

    void presentFailure(String message);
}
