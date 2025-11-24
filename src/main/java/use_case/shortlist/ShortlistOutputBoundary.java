package use_case.shortlist;

/**
 * TODO: Presenter API for shortlist updates.
 */
public interface ShortlistOutputBoundary {
    void present(ShortlistOutputData outputData);

    void presentFailure(String message);
}

