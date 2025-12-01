package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.host_dashboard.HostDashboardState;
import interface_adapter.host_dashboard.HostDashboardViewModel;
import org.junit.jupiter.api.Test;
import use_case.create_room.CreateRoomOutputData;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomPresenterTest {

    @Test
    void testPresenterUpdatesCreatedRoomViewModelAndNavigates() {

        CreateRoomViewModel createVM = new CreateRoomViewModel();
        HostDashboardViewModel createdVM = new HostDashboardViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        CreateRoomPresenter presenter = new CreateRoomPresenter(createVM, createdVM, viewManager);

        CreateRoomOutputData output =
                new CreateRoomOutputData("Alice", "ABC123");

        presenter.present(output);

        HostDashboardState state = createdVM.getState();
        assertEquals("ABC123", state.getRoomId());

        assertEquals("created room", viewManager.getActiveViewName());
    }

    @Test
    void testPresenterHandlesFailure() {
        CreateRoomViewModel createVM = new CreateRoomViewModel();
        HostDashboardViewModel createdVM = new HostDashboardViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        CreateRoomPresenter presenter = new CreateRoomPresenter(createVM, createdVM, viewManager);

        presenter.presentFailure("Something went wrong");

        assertEquals("Something went wrong", createVM.getState().getError());
    }
}