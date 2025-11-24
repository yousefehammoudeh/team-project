package interface_adapter.join_room;

import interface_adapter.ViewModel; //gives you access to getState() and setState()

/**
 * TODO: ViewModel wrapper for JoinRoomState.
 */
public class JoinRoomViewModel extends ViewModel<JoinRoomState> {
    // TODO: Initialize default state and helper methods
    private final String viewName = "Join Room";
    public static final String TITLE_LABEL = "Join Room View";
    public static final String USERNAME_LABEL = "Choose username";
    public static final String ROOM_CODE_LABEL = "Enter room code";
    public static final String JOIN_BUTTON_LABEL = "Join";
    public static final String CREATE_BUTTON_LABEL = "Create";

    public JoinRoomViewModel() {
        setState(new JoinRoomState());
    }

    public String getViewName() {
        return viewName;
    }
}