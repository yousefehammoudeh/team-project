package use_case.shortlist;

/**
 * TODO: Implements shortlist use case (add/remove/lock).
 */
public class ShortlistInteractor implements ShortlistInputBoundary {
    private final ShortlistUserDataAccessInterface gateway;
    private final ShortlistOutputBoundary presenter;

    public ShortlistInteractor(ShortlistUserDataAccessInterface gateway,
                               ShortlistOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    // TODO: Implement operations
}

