package use_case.join_room;

import entity.Participant;
import entity.Room;

/**
 * TODO: Implements join room use case.
 * - Validate code and name
 * - Add participant to room
 * - Return current room state
 */
public class JoinRoomInteractor implements JoinRoomInputBoundary {
    private final JoinRoomUserDataAccessInterface roomGateway;
    private final JoinRoomOutputBoundary presenter;
    private final Participant participant;

    public JoinRoomInteractor(JoinRoomUserDataAccessInterface roomGateway,
                              JoinRoomOutputBoundary presenter,
                              Participant participant) {
        this.roomGateway = roomGateway;
        this.presenter = presenter;
        this.participant = participant;
    }


    @Override
    public void execute(JoinRoomInputData joinRoomInputData) {
        if (roomGateway.existsByName(joinRoomInputData.getUsername())) { //way to store current users in the room
            presenter.presentFailure("User already exists.");
        }

    // TODO: check that the room code exists
        else if (!roomGateway.existsByCode(joinRoomInputData.getRoomcode())) { //store the room code
            presenter.presentFailure("Incorrect room code, enter again.");
        }
        else if ("".equals(joinRoomInputData.getRoomcode())) {
            presenter.presentFailure("Room code cannot be empty");
        }
        else if ("".equals(joinRoomInputData.getUsername())) {
            presenter.presentFailure("Username cannot be empty");
        }
        else {
            //TODO: Create a participant and get the room, and then make sure you show the user dashboard for the correct room
            final Participant user = new Participant(joinRoomInputData.getUsername());
            final Room r = roomGateway.get(joinRoomInputData.getRoomcode());
            roomGateway.saveUser(user, r);

            final JoinRoomOutputData joinRoomOutputData = new JoinRoomOutputData(r);
            presenter.prepareSuccessView(joinRoomOutputData);
        }
    }

    @Override
    public void switchToCreateRoomView() {
        presenter.switchToCreateRoomView();
    }
}

