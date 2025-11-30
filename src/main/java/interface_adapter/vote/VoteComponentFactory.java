package interface_adapter.vote;

import data_access.room.RoomDatabase;
import use_case.vote.VoteInputBoundary;
import use_case.vote.VoteInteractor;

/**
 * Simple Factory for creating Vote use case components.
 * Follows the Factory design pattern to encapsulate object creation logic.
 * 
 * Benefits:
 * - Centralizes creation logic in one place
 * - Reduces coupling between AppBuilder and concrete classes
 * - Makes it easier to change implementations
 */
public class VoteComponentFactory {

    /**
     * Creates a fully wired VoteController.
     * 
     * @param dataAccess the data access object for room operations
     * @param viewModel  the view model to be updated by the presenter
     * @return a ready-to-use VoteController
     */
    public static VoteController createVoteController(
            RoomDatabase dataAccess,
            VoteViewModel viewModel) {
        VotePresenter presenter = new VotePresenter(viewModel);
        VoteInputBoundary interactor = new VoteInteractor(dataAccess, presenter);
        return new VoteController(interactor);
    }
}
