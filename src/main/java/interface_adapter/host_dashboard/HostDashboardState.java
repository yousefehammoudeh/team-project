package interface_adapter.host_dashboard;

import java.util.ArrayList;
import java.util.List;

/**
 * Holds UI state for the host dashboard: room id and participants list.
 */
public class HostDashboardState {
    private String roomId;
    private List<String> participants = new ArrayList<>();
    private boolean locked;

    public String getRoomId() {
        return roomId;
    }

    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public List<String> getParticipants() {
        return participants;
    }

    public void setParticipants(List<String> participants) {
        this.participants = participants != null ? participants : new ArrayList<>();
    }
}
