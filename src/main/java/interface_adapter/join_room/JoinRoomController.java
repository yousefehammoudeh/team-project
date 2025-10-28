package interface_adapter.join_room;

import use_case.join_room.JoinRoomInputBoundary;

/**
 * TODO: Accepts code + name to join a room and delegates to interactor.
 */
public class JoinRoomController {
    private final JoinRoomInputBoundary interactor;

    public JoinRoomController(JoinRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method to trigger join (e.g., join(code, name))
}

