package interface_adapter.joined_room;

import interface_adapter.ViewManagerModel;
import use_case.joined_room.JoinedRoomOutputBoundary;
import interface_adapter.join_room.JoinRoomViewModel;
import use_case.joined_room.JoinedRoomOutputData;

import static interface_adapter.ViewManagerModel.WELCOME_VIEW;

/**
 * Presents joined-room outcomes (e.g., leave-room): clears state and navigates
 * back to the join view.
 */
public class JoinedRoomPresenter implements JoinedRoomOutputBoundary {
    private final JoinedRoomViewModel joinedRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public JoinedRoomPresenter(JoinRoomViewModel joinRoomViewModel,
            JoinedRoomViewModel joinedRoomViewModel,
            ViewManagerModel viewManagerModel) {
        this.joinedRoomViewModel = joinedRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView(JoinedRoomOutputData outputData) {
        // clear everything in the JoinedRoomViewModel's state
        joinedRoomViewModel.setState(new JoinedRoomState());

        // switch to the welcome view
        this.viewManagerModel.setActiveViewName(WELCOME_VIEW );
    }

    @Override
    public void presentFailure(String message) {
        final JoinedRoomState joinedRoomState = joinedRoomViewModel.getState();
        joinedRoomState.setRoomcodeError(message);
        joinedRoomViewModel.firePropertyChanged();
    }

}