package interface_adapter.create_room;

import interface_adapter.ViewModel;

/**
 * ViewModel wrapper for CreateRoomState.
 */
public class CreateRoomViewModel extends ViewModel<CreateRoomState> {

    public CreateRoomViewModel() {
        super("create room");
        setState(new CreateRoomState());
    }
}

