package interface_adapter.join_room;

import use_case.join_room.JoinRoomInputBoundary;
import use_case.join_room.JoinRoomInputData;

/**
 * TODO: Accepts code + name to join a room and delegates to interactor.
 */
public class JoinRoomController {
    private final JoinRoomInputBoundary interactor;

    public JoinRoomController(JoinRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method to trigger join (e.g., join(code, name))

    /**
     * Executes the Join Room Use Case.
     * @param username the username to join a room
     * @param roomcode the code to join a room
     */
    public void execute(String username, String roomcode) {
        final JoinRoomInputData joinRoomInputData = new JoinRoomInputData(
                username, roomcode);

        interactor.execute(joinRoomInputData);
    }

    /**
     * Executes the "switch to CreateRoom" Use Case.
     */
    public void switchToCreateRoomView() {
        interactor.switchToCreateRoomView();
    }
}