package use_case.update_room;

import use_case.shortlist.ShortlistOutputBoundary;

public class UpdateRoomInteractor implements UpdateRoomInputBoundary {
    UpdateRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
                                ShortlistOutputBoundary shortlistPresenter) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
    }

    public void execute() {

    }
}
