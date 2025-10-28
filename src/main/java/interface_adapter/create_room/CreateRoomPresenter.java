package interface_adapter.create_room;

import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;

/**
 * TODO: Translates interactor output to view model updates.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    private final CreateRoomViewModel viewModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {
        // TODO: Map output to state and fire change
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Update error state and fire change
    }
}

