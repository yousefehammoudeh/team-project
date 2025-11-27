package interface_adapter.create_room;

import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;

/**
 * Translates interactor output to view model updates.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    @SuppressWarnings("unused")
    private final CreateRoomViewModel viewModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {
        CreateRoomState state = viewModel.getState();

        state.setHostName(outputData.getHostName());
        state.setRoomCode(outputData.getRoomCode());
        state.setHostToken(outputData.getHostToken());
        state.setError(null);

        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        CreateRoomState state = viewModel.getState();
        state.setError(message);
        viewModel.firePropertyChanged();
    }
}
