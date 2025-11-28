package interface_adapter.shortlist;

import use_case.toggle_lock_room.ToggleLockRoomInputBoundary;

public class ToggleLockRoomController {
    private final ToggleLockRoomInputBoundary toggleLockRoomInteractor;

    public ToggleLockRoomController(ToggleLockRoomInputBoundary toggleLockRoomInteractor) {
        this.toggleLockRoomInteractor = toggleLockRoomInteractor;
    }

    public void execute() {
        toggleLockRoomInteractor.execute();
    }
}
