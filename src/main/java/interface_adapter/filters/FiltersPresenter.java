package interface_adapter.filters;

import use_case.filters.FiltersOutputBoundary;
import use_case.filters.FiltersOutputData;

/**
 * TODO: Presents active filters to the view model.
 */
public class FiltersPresenter implements FiltersOutputBoundary {
    private final FiltersViewModel viewModel;

    public FiltersPresenter(FiltersViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(FiltersOutputData outputData) {
        // TODO: Update state and notify
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Update error state and notify
    }
}

