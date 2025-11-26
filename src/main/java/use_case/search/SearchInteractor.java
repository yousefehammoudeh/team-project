package use_case.search;

/**
 * TODO: Implements search & details use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    @SuppressWarnings("unused")
    private final SearchUserDataAccessInterface gateway;
    @SuppressWarnings("unused")
    private final SearchOutputBoundary presenter;

    public SearchInteractor(SearchUserDataAccessInterface gateway, SearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    // TODO: Implement search/details operations
}
