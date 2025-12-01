package use_case.search;

import java.util.List;

import entity.Movie;
//

/**
 * Search Interactor.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchUserDataAccessInterface gateway;
    private final SearchOutputBoundary outputBoundary;

    public SearchInteractor(SearchUserDataAccessInterface gateway, SearchOutputBoundary outputBoundary) {
        this.gateway = gateway;
        this.outputBoundary = outputBoundary;
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
            outputBoundary.present(outputData);
        }
        catch (Exception e) {
            outputBoundary.presentFailure("Search failed: " + e.getMessage());
        }
    }

    @Override
    public void switchToShortlistView() {
        outputBoundary.switchToShortlistView();
    }

    @Override
    public void switchToCreatedRoomView() {
        outputBoundary.switchToCreatedRoomView();
    }
}
