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
    private final CreateRoomUserDataAccessInterface roomGateway;
    private final CreateRoomOutputBoundary presenter;

    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomGateway,
                                CreateRoomOutputBoundary presenter) {
        this.roomGateway = roomGateway;
        this.presenter = presenter;
    }

    public void execute(CreateRoomInputData createRoomInputData) {
        final String hostName = createRoomInputData.getHostName();
        final String roomCode = generateUniqueRoomCode();
        final String hostToken = generateToken();

        //TODO: Host cant create 2 rooms at the same time

        final Room room = new Room(roomCode);

        roomGateway.save(room);
        roomGateway.setCurrentRoom(roomCode);

        CreateRoomOutputData output = new CreateRoomOutputData(hostName, roomCode, hostToken);

        presenter.present(output);
    }

    private String generateUniqueRoomCode() {
        String code;
        do {
            code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);
        } while (roomGateway.existsByRoomCode(code));
        return code;
    }

    private String generateToken() {
        return UUID.randomUUID().toString();
    }
}

