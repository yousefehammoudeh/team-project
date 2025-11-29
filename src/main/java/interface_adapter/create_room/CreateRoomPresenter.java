package interface_adapter.create_room;

import use_case.create_room.CreateRoomOutputBoundary;
import use_case.create_room.CreateRoomOutputData;
import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;

public class CreateRoomPresenter implements CreateRoomOutputBoundary {

    private final CreateRoomViewModel createRoomViewModel;
    private final CreatedRoomViewModel createdRoomViewModel;
    private ViewManagerModel viewManagerModel;

    public CreateRoomPresenter(CreateRoomViewModel createVM, CreatedRoomViewModel createdVM) {
        this.createRoomViewModel = createVM;
        this.createdRoomViewModel = createdVM;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    @Override
    public void present(CreateRoomOutputData outputData) {

        CreatedRoomState state = createdRoomViewModel.getState();
        state.setRoomCode(outputData.getRoomCode());
        createdRoomViewModel.firePropertyChanged();

        viewManagerModel.setActiveViewName("created room");
        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        CreateRoomState state = createRoomViewModel.getState();
        state.setError(message);
        createRoomViewModel.firePropertyChanged();
    }
}
