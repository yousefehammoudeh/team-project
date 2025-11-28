package interface_adapter.create_room;

import use_case.create_room.CreateRoomInputBoundary;
import use_case.create_room.CreateRoomInputData;

/**
 * Accepts UI input to create a room and delegates to interactor.
 */
public class CreateRoomController {
    @SuppressWarnings("unused")
    private final CreateRoomInputBoundary interactor;

    public CreateRoomController(CreateRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute(String hostName) {
        if (hostName == null || hostName.trim().isEmpty()) {
            throw new IllegalArgumentException("Host name cannot be null or empty");
        }

        final CreateRoomInputData inputData = new CreateRoomInputData(hostName.trim());
        interactor.execute(inputData);
    }
}
