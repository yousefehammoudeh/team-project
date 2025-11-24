package use_case.shortlist;

import java.util.List;

/**
 * TODO: Output data for shortlist state.
 */
public class ShortlistOutputData {
    private final List<String> movieIDs; // TODO: might change to list of Movies
    private final boolean locked;

    public ShortlistOutputData(List<String> movieIDs, boolean locked) {
        this.movieIDs = movieIDs;
        this.locked = locked;
    }

    public List<String> getMovieIDs() {
        return movieIDs;
    }

    public boolean isLocked() {
        return locked;
    }
}

