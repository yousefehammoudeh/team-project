package interface_adapter.host_dashboard;

import data_access.room.RoomDatabase;
import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.joined_room.JoinedRoomViewModel;

import java.util.List;

/**
 * Simple controller to refresh participants and room code for participant
 * dashboard.
 */
public class ParticipantsRefreshController {
    private final RoomDatabase roomDb;
    private final JoinedRoomViewModel joinedRoomViewModel;

    public ParticipantsRefreshController(RoomDatabase roomDb, JoinedRoomViewModel joinedRoomViewModel) {
        this.roomDb = roomDb;
        this.joinedRoomViewModel = joinedRoomViewModel;
    }

    public void execute() {
        try {
            roomDb.refreshRoom();
            List<String> participants = roomDb.getParticipantIDs();
            boolean locked = roomDb.isShortlistLocked();
            System.out.println("[ParticipantsRefreshController] Read from DB: locked=" + locked + ", participants="
                    + participants.size());
            JoinedRoomState state = joinedRoomViewModel.getState();
            state.setParticipants(participants);
            state.setLocked(locked);
            // room code remains unchanged; ensure it is set once during join
            joinedRoomViewModel.firePropertyChanged();
            System.out.println("[ParticipantsRefreshController] Fired property change");
        } catch (Exception e) {
            System.err.println("[ParticipantsRefreshController] ERROR: " + e.getMessage());
        }
    }
}
