package interface_adapter.shortlist;

import use_case.shortlist.ShortlistInputBoundary;

/**
 * TODO: Add/remove candidates; lock shortlist (host only).
 */
public class ShortlistController {
    private final ShortlistInputBoundary interactor;

    public ShortlistController(ShortlistInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Methods: add(movieId), remove(movieId), lock(hostToken)
}

