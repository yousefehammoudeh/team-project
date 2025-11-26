package interface_adapter.shortlist;

import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

/**
 * TODO: Presents shortlist state and lock status to the view model.
 */
public class ShortlistPresenter implements ShortlistOutputBoundary {
    private final ShortlistViewModel viewModel;

    public ShortlistPresenter(ShortlistViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(ShortlistOutputData outputData) {
        final ShortlistState shortlistState = viewModel.getState();
        shortlistState.setShortlist(outputData.getShortlist());
        shortlistState.setLocked(outputData.isLocked());
        shortlistState.setError(null);
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        final ShortlistState shortlistState = viewModel.getState();
        shortlistState.setError(message);
        viewModel.firePropertyChanged();
    }
}

