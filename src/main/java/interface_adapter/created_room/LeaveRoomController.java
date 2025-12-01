package interface_adapter.created_room;

import use_case.leave_room.LeaveRoomInputBoundary;

public class LeaveRoomController {
    private final LeaveRoomInputBoundary interactor;

    public LeaveRoomController(LeaveRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.execute();
    }
}
