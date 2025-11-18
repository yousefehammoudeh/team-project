package use_case.create_room;

import entity.Room;

import java.util.UUID;

/**
 * Implements create room use case.
 * - Generate room code + host token
 * - Persist room
 * - Return dashboard initial state
 */
public class CreateRoomInteractor implements CreateRoomInputBoundary {
    // Implement execute(CreateRoomInputData inputData)
    // Implement execute(CreateRoomInputData inputData)
    private final CreateRoomUserDataAccessInterface roomGateway;
    private final CreateRoomOutputBoundary presenter;

    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomGateway,
            CreateRoomOutputBoundary presenter) {
        this.roomGateway = roomGateway;
        this.presenter = presenter;
    }

    // TODO: Implement execute(CreateRoomInputData inputData)
}
