package interface_adapter.join_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.CreatedRoomViewModel;
import use_case.join_room.JoinRoomOutputBoundary;
import use_case.join_room.JoinRoomOutputData;

/**
 * Presents join-room outcomes: updates view models and navigates accordingly.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {
    private final JoinRoomViewModel joinRoomViewModel;
    private final CreatedRoomViewModel createdRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public JoinRoomPresenter(JoinRoomViewModel joinRoomViewModel,
                             CreatedRoomViewModel createdRoomViewModel,
            ViewManagerModel viewManagerModel) {
        this.joinRoomViewModel = joinRoomViewModel;
        this.createdRoomViewModel = createdRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(JoinRoomOutputData outputData) {
        // on success, switch to createdRoomViewModel's state (user's dashboard)
        final CreatedRoomState createdRoomState = createdRoomViewModel.getState();
        createdRoomState.setParticipants(outputData.getParticipants());
        createdRoomState.setHostName(outputData.getHostName());
        createdRoomState.setRoomCode(outputData.getRoomCode());
        this.createdRoomViewModel.firePropertyChanged();

        // clear everything in the JoinRoomViewModel's state
        joinRoomViewModel.setState(new JoinRoomState());

        // switch to the created room view
        this.viewManagerModel.setActiveViewName(ViewManagerModel.CREATED_ROOM_VIEW);
    }

    @Override
    public void presentFailure(String message) {
        final JoinRoomState joinRoomState = joinRoomViewModel.getState();
        joinRoomState.setUsernameError(message);
        joinRoomViewModel.firePropertyChanged();
    }
}