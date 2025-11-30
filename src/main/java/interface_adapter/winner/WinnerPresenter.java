package interface_adapter.winner;

import use_case.winner.WinnerOutputBoundary;
import use_case.winner.WinnerOutputData;

/**
 * Presenter for winner use case.
 * Follows Clean Architecture: depends only on ViewModel (abstraction), not View
 * (detail).
 */
public class WinnerPresenter implements WinnerOutputBoundary {
    private final WinnerViewModel viewModel;

    public WinnerPresenter(WinnerViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(WinnerOutputData data) {
        WinnerState s = viewModel.getState();
        s.setTitle(data.getTitle());
        s.setDetails(data.getDetails());
        s.setPoster(data.getPoster());
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        WinnerState s = viewModel.getState();
        s.setTitle("Winner computation failed");
        s.setDetails(message);
        s.setPoster(null);
        viewModel.firePropertyChanged();
    }
}
