package use_case.join_room;

import data_access.note_database.DataAccessException;


import java.util.List;

/**
 * TODO: Implements join room use case.
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

            // check if the room exists, if it does add a participant
            boolean exists = roomGateway.joinRoom(joinRoomInputData.getRoomcode());
            boolean added = roomGateway.addParticipant(joinRoomInputData.getUsername());
            if(!added) { //participant username already used
                presenter.presentFailure("User already exists.");
                return;
            }
            if(!exists) {
                presenter.presentFailure("Room doesn't exist.");
                return;
            }
            else {
                List<String> p = roomGateway.getParticipantIDs();
                // when using the real database, add a saveroom step
                final JoinRoomOutputData joinRoomOutputData = new JoinRoomOutputData(p, joinRoomInputData.getUsername(),
                        joinRoomInputData.getRoomcode());
                presenter.prepareSuccessView(joinRoomOutputData);
            }

        }

    }

    @Override
    public void switchToCreateRoomView() {
        presenter.switchToCreateRoomView();
    }
}