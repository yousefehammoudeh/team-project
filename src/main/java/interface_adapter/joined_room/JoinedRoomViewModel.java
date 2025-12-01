package interface_adapter.joined_room;

import interface_adapter.ViewModel;

public class JoinedRoomViewModel extends ViewModel<JoinedRoomState> {
    public static final String EXIT_BUTTON_LABEL = "Leave Room";

    public JoinedRoomViewModel() {
        super("Joined Room");
        setState(new JoinedRoomState());
    }
}