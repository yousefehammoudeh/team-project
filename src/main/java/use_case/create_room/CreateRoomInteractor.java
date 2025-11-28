package use_case.create_room;

import data_access.note_database.DataAccessException;
import static data_access.HTTPCode.CONFLICT_ERROR;

/**
 * Implements create room use case.
 * - Generate room code
 * - Persist room
 * - Return dashboard initial state
 */
public class CreateRoomInteractor implements CreateRoomInputBoundary {
    private final CreateRoomUserDataAccessInterface roomDataAccess;
    private final CreateRoomOutputBoundary presenter;

    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomDataAccess,
            CreateRoomOutputBoundary presenter) {
        this.roomDataAccess = roomDataAccess;
        this.presenter = presenter;
    }

    public void execute(CreateRoomInputData createRoomInputData) {
        final String hostName = createRoomInputData.getHostName();

        // Currently not used beyond identity fetch; retained for future use-case rules.
        @SuppressWarnings("unused")
        final String hostId = roomDataAccess.getUsername();

        try {
            String roomCode;
            while (true) {
                roomCode = generateUniqueRoomCode();
                try {
                    roomDataAccess.createRoom(roomCode);
                    break; // success
                } catch (DataAccessException e) {
                    if (e.getCode() == CONFLICT_ERROR) {
                        // collision on code; try again
                        continue;
                    }
                    throw e;
                }
            }

            CreateRoomOutputData output = new CreateRoomOutputData(hostName, roomCode);
            presenter.present(output);

        } catch (DataAccessException e) {
            presenter.presentFailure("Error creating room: " + e.getMessage());
        }
    }

    private static String generateUniqueRoomCode() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final java.util.Random random = new java.util.Random();
        final StringBuilder code = new StringBuilder();
        for (int i = 0; i < 6; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}