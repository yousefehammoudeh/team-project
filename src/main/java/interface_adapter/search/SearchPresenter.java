package interface_adapter.search;

import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * TODO: Presents search results and details to the view model.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel viewModel;

    public SearchPresenter(SearchViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SearchOutputData outputData) {
        // Update view model fields
        SearchState state = viewModel.getState();
        state.setError(null);             // no error
        state.setMovies(outputData.getMovies());  // list<Movie>

        // Fire event so SearchView refreshes UI
        viewModel.firePropertyChanged();

        System.out.println("[Presenter] Updated results: " + outputData.getMovies().size());
    }

    @Override
    public void presentFailure(String message) {
        // Update only the error
        SearchState state = viewModel.getState();
        state.setError(message);
        state.setMovies(null);  // clear results

        // Fire update
        viewModel.firePropertyChanged();

        System.out.println("[Presenter] Error: " + message);
    }
}

