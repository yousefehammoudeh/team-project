package use_case.shortlist;

import java.util.List;

/**
 * TODO: Output data for shortlist state.
 */
public class ShortlistOutputData {
    private final List<String> shortlist; // TODO: might change to list of Movies
    private final boolean locked;

    public ShortlistOutputData(List<String> shortlist, boolean locked) {
        this.shortlist = shortlist;
        this.locked = locked;
    }

    public List<String> getShortlist() {
        return shortlist;
    }

    public boolean isLocked() {
        return locked;
    }
}

