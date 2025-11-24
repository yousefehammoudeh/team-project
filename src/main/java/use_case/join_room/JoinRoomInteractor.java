package use_case.join_room;

import data_access.note_database.DataAccessException;
import data_access.room.RoomDatabase;
import entity.Participant;
import entity.Room;

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
                // check if the room exists, if it does add a participant
                roomGateway.setUsername(joinRoomInputData.getUsername());
                boolean existsByName = roomGateway.joinRoom(joinRoomInputData.getRoomcode());
                if(existsByName) { //participant username already used
                    presenter.presentFailure("User already exists.");
                }
                else {
                    List<String> p = roomGateway.getParticipantIDs();

                    final JoinRoomOutputData joinRoomOutputData = new JoinRoomOutputData(p, joinRoomInputData.getRoomcode());
                    presenter.prepareSuccessView(joinRoomOutputData);
                }


            }
            catch (DataAccessException e) {
                // room doesnt exist, then call presenter
                presenter.presentFailure("Incorrect room code, enter again.");
            }
        }



    // TODO: check that the room code exists

        else {
            //TODO: Create a participant and get the room, and then make sure you show the user dashboard for the correct room



        }
    }

    @Override
    public void switchToCreateRoomView() {
        presenter.switchToCreateRoomView();
    }
}

