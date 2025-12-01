package use_case.leave_room;

public interface LeaveRoomOutputBoundary {
    void present();

    void presentFailure(String message);
}
