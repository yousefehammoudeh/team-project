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
        // TODO: Map to state and notify
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Update error state and notify
    }
}

