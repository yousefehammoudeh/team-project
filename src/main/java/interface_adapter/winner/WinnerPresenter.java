package interface_adapter.winner;

import view.WinnerView;

public class WinnerPresenter {
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

    public void present(WinnerState state) {
        WinnerState s = viewModel.getState();
        s.setTitle(state.getTitle());
        s.setDetails(state.getDetails());
        s.setPoster(state.getPoster());
        viewModel.firePropertyChanged();
    }
}
