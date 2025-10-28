package interface_adapter.suggestions;

import use_case.suggestions.SuggestionsOutputBoundary;
import use_case.suggestions.SuggestionsOutputData;

/**
 * TODO: Presents suggested movies to the view model.
 */
public class SuggestionsPresenter implements SuggestionsOutputBoundary {
    private final SuggestionsViewModel viewModel;

    public SuggestionsPresenter(SuggestionsViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(SuggestionsOutputData outputData) {
        // TODO: Update state and notify
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Update error state and notify
    }
}

