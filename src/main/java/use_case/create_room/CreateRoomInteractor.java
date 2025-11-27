package use_case.create_room;

import data_access.note_database.DataAccessException;
import entity.Room;

/**
 * Implements create room use case.
 * - Generate room code + host token
 * - Persist room
 * - Return dashboard initial state
 */
public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private final CreateRoomUserDataAccessInterface roomDataAccess;
    private final Room room;
    private final CreateRoomOutputBoundary presenter;

    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomDataAccess, Room room,
            CreateRoomOutputBoundary presenter) {
        this.roomDataAccess = roomDataAccess;
        this.room = room;
        this.presenter = presenter;
    }

    public void execute(CreateRoomInputData createRoomInputData) {
        final String hostName = createRoomInputData.getHostName();

        final String hostId = roomDataAccess.getUsername();

        try {
            if (roomDataAccess.verifyRoomUniquenessPerUser(hostId)) {
                presenter.presentFailure("The Host already created a room.");
                return;
            }

            final String roomCode = room.generateUniqueRoomCode();
            final String hostToken = room.generateToken();

            roomDataAccess.createRoom(roomCode);

            CreateRoomOutputData output = new CreateRoomOutputData(hostName, roomCode, hostToken);
            presenter.present(output);

        } catch (DataAccessException e) {
            presenter.presentFailure("Error creating room: " + e.getMessage());
        }
    }
}