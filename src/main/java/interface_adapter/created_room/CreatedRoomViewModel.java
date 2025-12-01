package interface_adapter.created_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

/**
 * ViewModel for the Created Room (host dashboard) view.
 */
public class CreatedRoomViewModel extends ViewModel<CreatedRoomState> {

    /**
     * Constructs a ViewModel for the Created Room view and initializes it
     * with a default {@link CreatedRoomState}.
     */
    public CreatedRoomViewModel() {
        super(ViewManagerModel.CREATED_ROOM_VIEW);
        setState(new CreatedRoomState());
    }
}