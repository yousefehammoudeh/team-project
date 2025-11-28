package interface_adapter.join_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.create_room.CreateRoomViewModel;
import use_case.join_room.JoinRoomOutputBoundary;
import use_case.join_room.JoinRoomOutputData;
import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.joined_room.JoinedRoomViewModel;

/**
 * TODO: Presents room state after joining.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {
    private final JoinRoomViewModel joinRoomViewModel;
    private final JoinedRoomViewModel joinedRoomViewModel;
    @SuppressWarnings("unused")
    private final CreateRoomViewModel createRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public JoinRoomPresenter(JoinRoomViewModel joinRoomViewModel,
            JoinedRoomViewModel joinedRoomViewModel,
            CreateRoomViewModel createRoomViewModel,
            ViewManagerModel viewManagerModel) {
        this.joinRoomViewModel = joinRoomViewModel;
        this.joinedRoomViewModel = joinedRoomViewModel;
        this.createRoomViewModel = createRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(JoinRoomOutputData outputData) {
        // TODO: Update view model and notify
        // on success, switch to joinedRoomViewModel's state (user's dashboard)
        final JoinedRoomState joinedRoomState = joinedRoomViewModel.getState();
        joinedRoomState.setParticipants(outputData.getParticipants());
        joinedRoomState.setRoomcode(outputData.getRoomCode());
        joinedRoomState.setCurrentUser(outputData.getCurrentUser());
        this.joinedRoomViewModel.firePropertyChanged();

        // clear everything in the JoinRoomViewModel's state
        joinRoomViewModel.setState(new JoinRoomState());

        // switch to the joined room view
        this.viewManagerModel.setActiveViewName(joinedRoomViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Set error state and notify
        final JoinRoomState joinRoomState = joinRoomViewModel.getState();
        joinRoomState.setUsernameError(message);
        joinRoomViewModel.firePropertyChanged();
    }

    public void switchToCreateRoomView() {
        // viewManagerModel.setActiveViewName(createRoomViewModel.getViewName());
        viewManagerModel.setActiveViewName("create room");
        viewManagerModel.firePropertyChanged();
    }
}