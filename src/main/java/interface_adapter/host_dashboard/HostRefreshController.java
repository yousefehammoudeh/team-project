package interface_adapter.host_dashboard;

import data_access.room.RoomDatabase;

import java.util.List;

/**
 * Simple controller to refresh participants for the host dashboard.
 */
public class HostRefreshController {
    private final RoomDatabase roomDb;
    private final HostDashboardViewModel hostDashboardViewModel;

    public HostRefreshController(RoomDatabase roomDb, HostDashboardViewModel hostDashboardViewModel) {
        this.roomDb = roomDb;
        this.hostDashboardViewModel = hostDashboardViewModel;
    }

    public void execute() {
        try {
            roomDb.refreshRoom();
            List<String> participants = roomDb.getParticipantIDs();
            HostDashboardState state = hostDashboardViewModel.getState();
            state.setParticipants(participants);
            state.setLocked(roomDb.isShortlistLocked());
            hostDashboardViewModel.firePropertyChanged();
        } catch (Exception ignored) {
        }
    }
}
