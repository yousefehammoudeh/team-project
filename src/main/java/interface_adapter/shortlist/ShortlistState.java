package interface_adapter.shortlist;

import java.util.List;

/**
 * TODO: Holds UI state for shortlist screen.
 * Fields to consider:
 * - candidateMovieIds
 * - locked
 * - error
 */
public class ShortlistState {
    private List<String> shortlist;
    private boolean locked;
    private String error;

    public List<String> getShortlist() {
        return shortlist;
    }

    public void setShortlist(List<String> shortlist) {
        this.shortlist = shortlist;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}

