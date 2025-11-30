package interface_adapter.winner;

import data_access.room.RoomDatabase;
import use_case.winner.WinnerInputBoundary;
import use_case.winner.WinnerInteractor;

/**
 * Simple Factory for creating Winner use case components.
 * Follows the Factory design pattern to encapsulate object creation logic.
 * 
 * Benefits:
 * - Centralizes creation logic in one place
 * - Reduces coupling between AppBuilder and concrete classes
 * - Makes it easier to change implementations
 */
public class WinnerComponentFactory {

    /**
     * Creates a fully wired WinnerController.
     * 
     * @param dataAccess the data access object for room operations
     * @param viewModel  the view model to be updated by the presenter
     * @return a ready-to-use WinnerController
     */
    public static WinnerController createWinnerController(
            RoomDatabase dataAccess,
            WinnerViewModel viewModel) {
        WinnerPresenter presenter = new WinnerPresenter(viewModel);
        WinnerInputBoundary interactor = new WinnerInteractor(dataAccess, presenter);
        return new WinnerController(interactor);
    }
}
