package use_case.search;

/**
 * TODO: Presenter API for search & details.
 */
public interface SearchOutputBoundary {
    void present(SearchOutputData outputData);
    void presentFailure(String message);
}

