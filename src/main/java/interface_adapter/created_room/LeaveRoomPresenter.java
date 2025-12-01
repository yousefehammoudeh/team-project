package interface_adapter.created_room;

import interface_adapter.ViewManagerModel;
import use_case.leave_room.LeaveRoomOutputBoundary;

public class LeaveRoomPresenter implements LeaveRoomOutputBoundary {
    private final CreatedRoomViewModel createdRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public LeaveRoomPresenter(CreatedRoomViewModel createdRoomViewModel, ViewManagerModel viewManagerModel) {
        this.createdRoomViewModel = createdRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present() {
        viewManagerModel.setActiveViewName(ViewManagerModel.WELCOME_VIEW);
    }

    @Override
    public void presentFailure(String message) {
        CreatedRoomState state = createdRoomViewModel.getState();
        state.setError(message);
        createdRoomViewModel.firePropertyChanged();
    }
}
