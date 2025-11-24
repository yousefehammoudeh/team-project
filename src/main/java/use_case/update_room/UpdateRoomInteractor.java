package use_case.update_room;

import data_access.note_database.DataAccessException;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import static data_access.HTTPCode.TOO_MANY_REQUESTS;

public class UpdateRoomInteractor implements UpdateRoomInputBoundary {
    private final static int COOLDOWN = 20;
    UpdateRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;
    private boolean inCooldown;

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
                                ShortlistOutputBoundary shortlistPresenter) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
    }

    public void execute() {
        if (inCooldown) {
            return;
        }
        try {
            roomDataAccessObject.refreshRoom();
            final ShortlistOutputData shortlistOutputData =
                    new ShortlistOutputData(roomDataAccessObject.getShortlist(), roomDataAccessObject.isLocked());
            shortlistPresenter.present(shortlistOutputData);
        }
        catch (DataAccessException ex) {
            if (ex.getCode() == TOO_MANY_REQUESTS) {
                inCooldown = true;
                new Thread(() -> {
                    try {
                        Thread.sleep(COOLDOWN * 1000);
                        inCooldown = false;
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                }).start();
                shortlistPresenter.presentFailure(
                        String.format("Too many requests. Next update will take place after %d seconds", COOLDOWN));
            }
            else {
                shortlistPresenter.presentFailure(ex.getMessage());
            }
        }
    }
}
