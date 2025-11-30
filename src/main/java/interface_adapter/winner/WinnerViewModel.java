package interface_adapter.winner;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

/**
 * ViewModel wrapper for WinnerState.
 * Follows Observer pattern as the Observable component.
 * 
 * Responsibilities:
 * - Hold the observable WinnerState instance
 * - Notify observers when state changes
 */
public class WinnerViewModel extends ViewModel<WinnerState> {

    public WinnerViewModel() {
        super(ViewManagerModel.WINNER_VIEW);
        this.state = new WinnerState();
    }

    public WinnerState getWinnerState() {
        return this.state;
    }
}
