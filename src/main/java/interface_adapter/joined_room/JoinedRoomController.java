package interface_adapter.joined_room;

import use_case.joined_room.JoinedRoomInputBoundary;

/**
 * Controller that delegates joined-room actions to the interactor.
 */
public class JoinedRoomController {
    private final JoinedRoomInputBoundary interactor;

    public JoinedRoomController(JoinedRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String username) {
        interactor.execute(username);
    }

}