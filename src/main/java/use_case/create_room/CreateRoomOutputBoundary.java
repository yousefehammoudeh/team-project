package use_case.create_room;

/**
 * TODO: Presenter API for create room success/failure.
 */
public interface CreateRoomOutputBoundary {
    void present(CreateRoomOutputData outputData); // TODO: refine
    void presentFailure(String message);
}

