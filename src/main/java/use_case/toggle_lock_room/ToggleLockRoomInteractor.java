package use_case.toggle_lock_room;

import data_access.note_database.DataAccessException;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

public class ToggleLockRoomInteractor implements ToggleLockRoomInputBoundary {
    ToggleLockRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;

    public ToggleLockRoomInteractor(ToggleLockRoomDataAccessInterface roomDataAccessObject,
                                    ShortlistOutputBoundary shortlistPresenter) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
    }

    public void execute() {
        try {
            if (roomDataAccessObject.isHost()) {
                boolean isLocked = roomDataAccessObject.isLocked();
                roomDataAccessObject.setLocked(!isLocked);
                final ShortlistOutputData shortlistOutputData =
                        new ShortlistOutputData(roomDataAccessObject.getShortlist(), !isLocked);
                shortlistPresenter.present(shortlistOutputData);
            }
            else {
                shortlistPresenter.presentFailure("Only the host can lock the room.");
            }
        }
        catch (DataAccessException e) {
            shortlistPresenter.presentFailure(e.getMessage());
        }
    }
}
