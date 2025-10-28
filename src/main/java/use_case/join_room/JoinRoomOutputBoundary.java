package use_case.join_room;

/**
 * TODO: Presenter API for join room.
 */
public interface JoinRoomOutputBoundary {
    void present(JoinRoomOutputData outputData);
    void presentFailure(String message);
}

