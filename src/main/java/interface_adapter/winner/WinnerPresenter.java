package interface_adapter.winner;

import view.WinnerView;
import use_case.winner.WinnerOutputBoundary;
import use_case.winner.WinnerOutputData;

public class WinnerPresenter implements WinnerOutputBoundary {
    private final WinnerViewModel viewModel;
    private final WinnerView view;

    public WinnerPresenter(WinnerViewModel viewModel, WinnerView view) {
        this.viewModel = viewModel;
        this.view = view;
        this.viewModel.addPropertyChangeListener(evt -> {
            WinnerState s = (WinnerState) evt.getNewValue();
            if (s == null)
                return;
            view.setWinnerTitle(s.getTitle());
            view.setPoster(s.getPoster());
            view.setDetails(s.getDetails());
        });
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
