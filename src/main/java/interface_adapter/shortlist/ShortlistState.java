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
    private List<String> movieIDs;
    private boolean locked;

    public List<String> getMovieIDs() {
        return movieIDs;
    }

    public void setMovieIDs(List<String> movieIDs) {
        this.movieIDs = movieIDs;
    }

    public boolean isLocked() {
        return locked;
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }
}

