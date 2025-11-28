package interface_adapter.joined_room;

import interface_adapter.ViewModel;

public class JoinedRoomViewModel extends ViewModel<JoinedRoomState> {
    private final String viewName = "Joined Room";
    public static final String TITLE_LABEL = "Waiting for Host to Select Shortlist...";
    public static final String EXIT_BUTTON_LABEL = "Leave Room";

    public JoinedRoomViewModel() {
        setState(new JoinedRoomState());
    }

    public String getViewName() {
        return viewName;
    }
}