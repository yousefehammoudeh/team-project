package interface_adapter.search;

import use_case.search.SearchInputBoundary;

/**
 * TODO: Accepts search queries and delegates to interactor.
 */
public class SearchController {
    private final SearchInputBoundary interactor;

    public SearchController(SearchInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method to trigger search (e.g., search(query))
}

