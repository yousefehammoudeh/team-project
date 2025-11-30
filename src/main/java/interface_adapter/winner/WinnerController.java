package interface_adapter.winner;

import use_case.winner.WinnerInputBoundary;

public class WinnerController {
    private final WinnerInputBoundary interactor;

    public WinnerController(WinnerInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void execute() {
        interactor.computeWinner();
    }

    public void displayWinner() {
        interactor.displayWinner();
    }
}
