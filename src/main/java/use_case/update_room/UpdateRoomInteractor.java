package use_case.update_room;

import data_access.note_database.DataAccessException;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import static data_access.HTTPCode.TOO_MANY_REQUESTS;

public class UpdateRoomInteractor implements UpdateRoomInputBoundary {
    UpdateRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
                                ShortlistOutputBoundary shortlistPresenter) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
    }

    public void execute() {
        try {
            roomDataAccessObject.refreshRoom();
            final ShortlistOutputData shortlistOutputData =
                    new ShortlistOutputData(roomDataAccessObject.getShortlist(), roomDataAccessObject.isLocked());
            shortlistPresenter.present(shortlistOutputData);
        }
        catch (DataAccessException ex) {
            if (ex.getCode() != TOO_MANY_REQUESTS) {
                shortlistPresenter.presentFailure(ex.getMessage());
            }
        }
    }
}
