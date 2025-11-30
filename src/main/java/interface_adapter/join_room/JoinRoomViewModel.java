package interface_adapter.join_room;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

/**
 * ViewModel wrapper for JoinRoomState.
 */
public class JoinRoomViewModel extends ViewModel<JoinRoomState> {
    public static final String TITLE_LABEL = "Join Room View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String ROOM_CODE_LABEL = "Enter room code";
    public static final String JOIN_BUTTON_LABEL = "Join";
    public static final String CREATE_BUTTON_LABEL = "Create";

    public JoinRoomViewModel() {
        super(ViewManagerModel.JOIN_ROOM_VIEW);
        setState(new JoinRoomState());
    }
    // Uses base getViewName()
}
