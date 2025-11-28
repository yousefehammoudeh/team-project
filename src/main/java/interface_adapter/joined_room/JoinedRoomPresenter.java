package interface_adapter.joined_room;

import interface_adapter.ViewManagerModel;
import use_case.joined_room.JoinedRoomOutputBoundary;
import interface_adapter.join_room.JoinRoomViewModel;

/**
 * Presents joined-room outcomes (e.g., leave-room): clears state and navigates
 * back to the join view.
 */
public class JoinedRoomPresenter implements JoinedRoomOutputBoundary {
    private final JoinRoomViewModel joinRoomViewModel;
    private final JoinedRoomViewModel joinedRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public JoinedRoomPresenter(JoinRoomViewModel joinRoomViewModel,
            JoinedRoomViewModel joinedRoomViewModel,
            ViewManagerModel viewManagerModel) {
        this.joinRoomViewModel = joinRoomViewModel;
        this.joinedRoomViewModel = joinedRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void prepareSuccessView() {
        // clear everything in the JoinRoomViewModel's state
        joinedRoomViewModel.setState(new JoinedRoomState());

        // switch to the join room view
        this.viewManagerModel.setActiveViewName(joinRoomViewModel.getViewName());
        this.viewManagerModel.firePropertyChanged();
    }

}