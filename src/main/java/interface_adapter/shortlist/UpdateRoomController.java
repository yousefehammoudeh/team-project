package interface_adapter.shortlist;

import use_case.update_room.UpdateRoomInputBoundary;

public class UpdateRoomController {
    private final UpdateRoomInputBoundary updateRoomInputBoundary;

    public UpdateRoomController(UpdateRoomInputBoundary updateRoomInputBoundary) {
        this.updateRoomInputBoundary = updateRoomInputBoundary;
    }

    public void execute() {
        updateRoomInputBoundary.execute();
    }
}
