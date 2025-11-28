package use_case.joined_room;

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

        roomGateway.leaveRoom(roomCode);
        presenter.prepareSuccessView();

    }
}