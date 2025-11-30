package use_case.joined_room;

import data_access.note_database.DataAccessException;

public class JoinedRoomInteractor implements JoinedRoomInputBoundary {
    private JoinedRoomUserDataAccessInterface roomGateway;
    private JoinedRoomOutputBoundary presenter;

    public JoinedRoomInteractor(JoinedRoomUserDataAccessInterface joinedRoomUserDataAccessInterface,
            JoinedRoomOutputBoundary joinedRoomOutputBoundary) {
        this.roomGateway = joinedRoomUserDataAccessInterface;
        this.presenter = joinedRoomOutputBoundary;
    };

    @Override
    public void execute(String roomCode) {

        try {
            roomGateway.leaveRoom(roomCode);
        } catch (DataAccessException e) {
            // TODO
        }
        presenter.prepareSuccessView();

    }
}