package interface_adapter.joined_room;

import use_case.join_room.JoinRoomInputData;
import use_case.joined_room.JoinedRoomInputBoundary;
import use_case.joined_room.JoinedRoomInputData;

/**
 * Controller that delegates joined-room actions to the interactor.
 */
public class JoinedRoomController {
    private final JoinedRoomInputBoundary interactor;

    public JoinedRoomController(JoinedRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String roomCode) {
        final JoinedRoomInputData joinedRoomInputData =
                new JoinedRoomInputData(roomCode);
        interactor.execute(joinedRoomInputData);
    }

}