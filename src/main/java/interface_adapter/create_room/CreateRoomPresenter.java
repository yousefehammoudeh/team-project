package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.CreatedRoomViewModel;
import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;

import java.util.List;

/**
 * Translates interactor output to view model updates and navigates to host
 * dashboard on success.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    private final CreateRoomViewModel viewModel;
    private final CreatedRoomViewModel createdRoomViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel, CreatedRoomViewModel createdRoomViewModel,
            ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.createdRoomViewModel = createdRoomViewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {
        CreateRoomState state = viewModel.getState();

        state.setHostName(outputData.getHostName());
        state.setRoomCode(outputData.getRoomCode());
        state.setError(null);

        viewModel.firePropertyChanged();

        // Update host dashboard state from output
        CreatedRoomState createdRoomState = createdRoomViewModel.getState();
        createdRoomState.setRoomCode(outputData.getRoomCode());
        createdRoomState.setHostName(outputData.getHostName());
        createdRoomState.setParticipants(List.of(outputData.getHostName()));
        createdRoomState.setError(null);
        createdRoomViewModel.firePropertyChanged();

        // Navigate to host dashboard
        viewManagerModel.setActiveViewName(ViewManagerModel.CREATED_ROOM_VIEW);
    }

    @Override
    public void presentFailure(String message) {
        CreateRoomState state = viewModel.getState();
        state.setError(message);
        viewModel.firePropertyChanged();
    }
}
