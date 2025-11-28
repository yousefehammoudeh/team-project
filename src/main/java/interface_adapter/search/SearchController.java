package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;

/**
 * TODO: Accepts search queries and delegates to interactor.
 */
public class SearchController {
    private final SearchInputBoundary searchInputBoundary;

    public SearchController(SearchInputBoundary searchInputBoundary) {
        this.searchInputBoundary = searchInputBoundary;
    }

    public void execute(String movieTitle) {
        final SearchInputData searchMovieInputData = new SearchInputData(movieTitle);
        searchInputBoundary.execute(searchMovieInputData);
    }

    public void switchToShortlistView() {
        searchInputBoundary.switchToShortlistView();
    }
    public void switchToHostDashboardView() {
        searchInputBoundary.switchToHostDashboardView();
    }
}
