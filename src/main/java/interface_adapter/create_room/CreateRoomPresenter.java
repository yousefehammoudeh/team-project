package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.host_dashboard.HostDashboardState;
import interface_adapter.host_dashboard.HostDashboardViewModel;
import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;

/**
 * Translates interactor output to view model updates and navigates to host
 * dashboard on success.
 */
public class CreateRoomPresenter implements CreateRoomOutputBoundary {
    private final CreateRoomViewModel viewModel;
    private final HostDashboardViewModel hostDashboardViewModel;
    private final ViewManagerModel viewManagerModel;

    public CreateRoomPresenter(CreateRoomViewModel viewModel, HostDashboardViewModel hostDashboardViewModel,
            ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.hostDashboardViewModel = hostDashboardViewModel;
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
        HostDashboardState hostState = hostDashboardViewModel.getState();
        hostState.setRoomId(outputData.getRoomCode());
        hostState.setParticipants(java.util.List.of(outputData.getHostName()));
        hostDashboardViewModel.firePropertyChanged();

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
