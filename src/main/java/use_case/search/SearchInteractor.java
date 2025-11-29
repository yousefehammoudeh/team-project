package use_case.search;

import entity.Movie;

//
import java.util.List;

/**
 * TODO: Implements search & details use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchUserDataAccessInterface gateway;
    private final SearchOutputBoundary presenter;

    public SearchInteractor(SearchUserDataAccessInterface gateway, SearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void execute(SearchInputData searchInputData) {
        final String movieTitle = searchInputData.getMovieTitle();

        try {
            List<Movie> movies = gateway.search(movieTitle);

            // list of movies
            if (movies.size() > 5) {
                movies = movies.subList(0, 5);
            }

            // search output data
            SearchOutputData outputData = new SearchOutputData(movies);

            // presenter
            presenter.present(outputData);

        } catch (Exception e) {
            presenter.presentFailure("Search failed: " + e.getMessage());
        }
    }

    public void switchToShortlistView() {
        presenter.switchToShortlistView();
    }

    public void switchToHostDashboardView() {
        presenter.switchToHostDashboardView();
    }
}