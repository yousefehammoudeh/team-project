package interface_adapter.create_room;

import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;
import interface_adapter.ViewManagerModel;

/**
 * Translates interactor output to view model updates.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    @SuppressWarnings("unused")
    private final CreateRoomViewModel viewModel;
    private ViewManagerModel viewManagerModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel) {
        this.viewModel = viewModel;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {
        CreateRoomState state = viewModel.getState();

        state.setHostName(outputData.getHostName());
        state.setRoomCode(outputData.getRoomCode());
        state.setError(null);

        viewManagerModel.setActiveViewName("created room");

        viewModel.firePropertyChanged();
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        CreateRoomState state = viewModel.getState();
        state.setError(message);
        viewModel.firePropertyChanged();
    }
}
