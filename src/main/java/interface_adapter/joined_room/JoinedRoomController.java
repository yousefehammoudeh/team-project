package interface_adapter.joined_room;

import use_case.joined_room.JoinedRoomInputBoundary;

import use_case.joined_room.JoinedRoomInputBoundary;

/**
 * TODO: Accepts code + name to join a room and delegates to interactor.
 */
public class JoinedRoomController {
    private final JoinedRoomInputBoundary interactor;

    public JoinedRoomController(JoinedRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method to trigger join (e.g., join(code, name))


    public void execute(String username) {
        interactor.execute(username);
    }

}