package interface_adapter.created_room;

import interface_adapter.ViewModel;

public class CreatedRoomViewModel extends ViewModel<CreatedRoomState> {

    public CreatedRoomViewModel() {
        super("created room");
        setState(new CreatedRoomState());
    }
}