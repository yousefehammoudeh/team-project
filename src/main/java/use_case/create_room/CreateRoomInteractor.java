package use_case.create_room;

/**
 * TODO: Implements create room use case.
 * - Generate room code + host token
 * - Persist room
 * - Return dashboard initial state
 */
public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private final CreateRoomUserDataAccessInterface roomGateway;
    private final CreateRoomOutputBoundary presenter;

    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomGateway,
                                CreateRoomOutputBoundary presenter) {
        this.roomGateway = roomGateway;
        this.presenter = presenter;
    }

    // TODO: Implement execute(CreateRoomInputData inputData)
}

