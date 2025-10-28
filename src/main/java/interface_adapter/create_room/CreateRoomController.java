package interface_adapter.create_room;

import use_case.create_room.CreateRoomInputBoundary;

/**
 * TODO: Accepts UI input to create a room and delegates to interactor.
 */
public class CreateRoomController {
    private final CreateRoomInputBoundary interactor;

    public CreateRoomController(CreateRoomInputBoundary interactor) {
        this.interactor = interactor; // TODO: Add null checks if needed
    }

    // TODO: Method to trigger room creation (e.g., createRoom())
}

