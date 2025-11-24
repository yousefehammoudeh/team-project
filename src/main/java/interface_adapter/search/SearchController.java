package interface_adapter.search;

import use_case.search.SearchInputBoundary;
import use_case.search.SearchInputData;
import use_case.search.SearchInteractor;

/**
 * TODO: Accepts search queries and delegates to interactor.
 */
public class SearchController {
    private final SearchInputBoundary searchInteractor;

    public SearchController(SearchInputBoundary searchInteractor) {
        this.searchInteractor = searchInteractor;
    }

    public void execute(String movieTitle) {
        final SearchInputData searchMovieInputData = new SearchInputData(movieTitle);
        searchInteractor.execute(searchMovieInputData);
    }

    public void switchToShortlistView() {
        searchInteractor.switchToShortlistView();
    }
}

