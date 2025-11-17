package use_case.joined_room;

import use_case.joined_room.JoinedRoomInputData;

public class JoinedRoomInteractor implements JoinedRoomInputBoundary{

    public JoinedRoomInteractor() {
        System.out.println("JoinedRoomInteractor constructor");
    };

    @Override
    public void execute(JoinedRoomInputData joinedRoomInputData) {
        System.out.println("JoinedRoomInteractor - test");
    }
}
