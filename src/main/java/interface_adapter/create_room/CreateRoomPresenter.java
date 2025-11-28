package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;

/**
 * Translates interactor output to view model updates and navigates to host dashboard on success.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    private final CreateRoomViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {
        CreateRoomState state = viewModel.getState();

        state.setHostName(outputData.getHostName());
        state.setRoomCode(outputData.getRoomCode());
        state.setError(null);

        viewModel.firePropertyChanged();

        // Navigate to host dashboard
        viewManagerModel.setActiveViewName(ViewManagerModel.HOST_DASHBOARD_VIEW);
    }

    @Override
    public void presentFailure(String message) {
        CreateRoomState state = viewModel.getState();
        state.setError(message);
        viewModel.firePropertyChanged();
    }
}
