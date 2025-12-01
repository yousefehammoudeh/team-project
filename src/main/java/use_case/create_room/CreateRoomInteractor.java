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
    private static final int NUMBER_OF_CODE = 6;
    private final CreateRoomUserDataAccessInterface roomDataAccess;
    private final CreateRoomOutputBoundary presenter;

    /**
     * Constructs the interactor for the Create Room use case.
     *
     * @param roomDataAccess the data access interface used to persist room data
     * @param presenter the output boundary used to present success or failure
     */
    public CreateRoomInteractor(CreateRoomUserDataAccessInterface roomDataAccess,
            CreateRoomOutputBoundary presenter) {
        this.roomDataAccess = roomDataAccess;
        this.presenter = presenter;
    }

    /**
     * Executes the Create Room use case.
     *
     * @param createRoomInputData the input data containing the host name
     */
    @Override
    public void execute(CreateRoomInputData createRoomInputData) {
        final String hostName = createRoomInputData.getHostName();

        try {
            roomDataAccess.setUsername(hostName);

            String roomCode;
            while (true) {
                roomCode = generateUniqueRoomCode();
                try {
                    roomDataAccess.createRoom(roomCode);
                    break;
                } catch (DataAccessException e) {
                    if (e.getCode() == CONFLICT_ERROR) {
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

    /**
     * Generates a random 6-character alphanumeric room code.
     *
     * @return a new randomly generated room code
     */
    private static String generateUniqueRoomCode() {
        final String characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
        final java.util.Random random = new java.util.Random();
        final StringBuilder code = new StringBuilder();
        for (int i = 0; i < NUMBER_OF_CODE; i++) {
            code.append(characters.charAt(random.nextInt(characters.length())));
        }
        return code.toString();
    }
}