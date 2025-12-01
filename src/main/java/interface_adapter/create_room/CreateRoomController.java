package interface_adapter.create_room;

import use_case.create_room.CreateRoomInputBoundary;
import use_case.create_room.CreateRoomInputData;

/**
 * Accepts UI input to create a room and delegates to interactor.
 */
public class CreateRoomController {
    @SuppressWarnings("unused")
    private final CreateRoomInputBoundary interactor;

    /**
     * Constructs a controller for the Create Room use case.
     *
     * @param interactor the input boundary (use-case interactor) to delegate to
     */
    public CreateRoomController(CreateRoomInputBoundary interactor) {
        this.interactor = interactor;
    }

    /**
     * Executes the Create Room use case using the provided host name.
     * Performs basic validation, trims whitespace, constructs the use-case input
     * data, and delegates the call to the interactor.
     *
     * @param hostName the name of the host creating the room
     * @throws IllegalArgumentException if the host name is null or empty
     */
    public void execute(String hostName) {
        if (hostName == null || hostName.trim().isEmpty()) {
            throw new IllegalArgumentException("Host name cannot be null or empty");
        }

        final CreateRoomInputData inputData = new CreateRoomInputData(hostName.trim());
        interactor.execute(inputData);
    }
}
