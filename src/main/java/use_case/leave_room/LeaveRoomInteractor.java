package use_case.leave_room;

import data_access.note_database.DataAccessException;
import interface_adapter.created_room.LeaveRoomPresenter;

public class LeaveRoomInteractor implements LeaveRoomInputBoundary {
    private final LeaveRoomDataAccessInterface roomDataAccessInterface;
    private final LeaveRoomOutputBoundary presenter;

    public LeaveRoomInteractor(LeaveRoomDataAccessInterface roomDataAccessInterface,
                                   LeaveRoomOutputBoundary presenter) {
        this.roomDataAccessInterface = roomDataAccessInterface;
        this.presenter = presenter;
    }

    public void execute() {
        try {
            roomDataAccessInterface.leaveRoom();
            presenter.present();
        }
        catch (DataAccessException e) {
            presenter.presentFailure(e.getMessage());
        }
    }
}
