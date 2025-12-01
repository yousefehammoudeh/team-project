package interface_adapter.search;

import interface_adapter.ViewManagerModel;
import use_case.search.SearchOutputBoundary;
import use_case.search.SearchOutputData;

/**
 * TODO: Presents search results and details to the view model.
 */
public class SearchPresenter implements SearchOutputBoundary {
    private final SearchViewModel viewModel;
    private final ViewManagerModel viewManagerModel;

    public SearchPresenter(SearchViewModel viewModel, ViewManagerModel viewManagerModel) {
        this.viewModel = viewModel;
        this.viewManagerModel = viewManagerModel;
    }

    @Override
    public void present(SearchOutputData outputData) {
        // Update view model fields
        final SearchState state = viewModel.getState();
        // no error
        state.setError(null);
        // list<Movie>
        state.setMovies(outputData.getMovies());

        // Fire event so SearchView refreshes UI
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        // Update only the error
        final SearchState state = viewModel.getState();
        state.setError(message);
        // clear results
        state.setMovies(null);

        // Fire update
        viewModel.firePropertyChanged();
    }

    @Override
    public void switchToShortlistView() {
        viewManagerModel.setActiveViewName(ViewManagerModel.SHORTLIST_VIEW);
    }

    @Override
    public void switchToCreatedRoomView() {
        viewManagerModel.setActiveViewName(ViewManagerModel.CREATED_ROOM_VIEW);
    }
}
