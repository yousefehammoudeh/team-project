package interface_adapter.create_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

/**
 * ViewModel wrapper for CreateRoomState.
 */
public class CreateRoomViewModel extends ViewModel<CreateRoomState> {

    /**
     * Constructs a ViewModel for the Create Room screen and initializes
     * it with a default {@link CreateRoomState}.
     */
    public CreateRoomViewModel() {
        super(ViewManagerModel.CREATE_ROOM_VIEW);
        setState(new CreateRoomState());
    }
}
