package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.CreatedRoomViewModel;
import org.junit.jupiter.api.Test;
import use_case.create_room.CreateRoomOutputData;

import static org.junit.jupiter.api.Assertions.*;

class CreateRoomPresenterTest {

    @Test
    void testPresenterUpdatesCreatedRoomViewModelAndNavigates() {

        CreateRoomViewModel createVM = new CreateRoomViewModel();
        CreatedRoomViewModel createdVM = new CreatedRoomViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        CreateRoomPresenter presenter = new CreateRoomPresenter(createVM, createdVM, viewManager);

        CreateRoomOutputData output =
                new CreateRoomOutputData("Alice", "ABC123");

        presenter.present(output);

        CreatedRoomState state = createdVM.getState();
        assertEquals("ABC123", state.getRoomCode());

        assertEquals("created room", viewManager.getActiveViewName());
    }

    @Test
    void testPresenterHandlesFailure() {
        CreateRoomViewModel createVM = new CreateRoomViewModel();
        CreatedRoomViewModel createdVM = new CreatedRoomViewModel();
        ViewManagerModel viewManager = new ViewManagerModel();

        CreateRoomPresenter presenter = new CreateRoomPresenter(createVM, createdVM, viewManager);

        presenter.presentFailure("Something went wrong");

        assertEquals("Something went wrong", createVM.getState().getError());
    }
}