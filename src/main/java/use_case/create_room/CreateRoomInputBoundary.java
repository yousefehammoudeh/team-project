package use_case.create_room;

/**
 * Interactor API for creating a room.
 */
public interface CreateRoomInputBoundary {

    /**
     * Constructs the input boundary for a successful create-room use case.
     *
     * @param createRoomInputData for input data.
     */
    void execute(CreateRoomInputData createRoomInputData);
}

