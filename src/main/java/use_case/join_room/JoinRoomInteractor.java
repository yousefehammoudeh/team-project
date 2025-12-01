package use_case.join_room;

import java.util.List;

import data_access.note_database.DataAccessException;
import static data_access.HTTPCode.NOT_FOUND_ERROR;

/**
 * - Validate code and name
 * - Add participant to room
 * - Return current room state
 */
public class JoinRoomInteractor implements JoinRoomInputBoundary {
    private final JoinRoomUserDataAccessInterface roomGateway;
    private final JoinRoomOutputBoundary presenter;

    public JoinRoomInteractor(JoinRoomUserDataAccessInterface roomGateway,
            JoinRoomOutputBoundary presenter) {
        this.roomGateway = roomGateway;
        this.presenter = presenter;
    }

    @Override
    public void execute(JoinRoomInputData joinRoomInputData) {
        // check for empty inputs

        if ("".equals(joinRoomInputData.getUsername())) {
            presenter.presentFailure("Username cannot be empty");
        }
        else if ("".equals(joinRoomInputData.getRoomcode())) {
            presenter.presentFailure("Room code cannot be empty");
        }
        // check if inputs are valid
        else {
            try {
                roomGateway.setUsername(joinRoomInputData.getUsername());
                final boolean added = roomGateway.joinRoom(joinRoomInputData.getRoomcode());
                if (!added) {
                    presenter.presentFailure("User already exists.");
                }
                else {
                    final List<String> p = roomGateway.getParticipantIDs();
                    final JoinRoomOutputData joinRoomOutputData = new JoinRoomOutputData(p,
                            roomGateway.getHostId(),
                            joinRoomInputData.getRoomcode());
                    presenter.prepareSuccessView(joinRoomOutputData);
                }
            }
            catch (DataAccessException e) {
                if (e.getCode() == NOT_FOUND_ERROR) {
                    presenter.presentFailure("Room doesn't exist.");
                }
                else {
                    presenter.presentFailure(e.getMessage());
                }
            }

        }

    }

    @Override
    public void switchToWelcomeView() {
        presenter.switchToWelcomeView();
    }
}
