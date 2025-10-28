package interface_adapter.join_room;

import use_case.join_room.JoinRoomOutputBoundary;
import use_case.join_room.JoinRoomOutputData;

/**
 * TODO: Presents room state after joining.
 */
public class JoinRoomPresenter implements JoinRoomOutputBoundary {
    private final JoinRoomViewModel viewModel;

    public JoinRoomPresenter(JoinRoomViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(JoinRoomOutputData outputData) {
        // TODO: Update view model and notify
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Set error state and notify
    }
}

