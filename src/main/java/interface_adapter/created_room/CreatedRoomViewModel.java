package interface_adapter.created_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

public class CreatedRoomViewModel extends ViewModel<CreatedRoomState> {

    public CreatedRoomViewModel() {
        super(ViewManagerModel.CREATED_ROOM_VIEW);
        setState(new CreatedRoomState());
    }
}