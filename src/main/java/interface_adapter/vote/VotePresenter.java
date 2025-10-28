package interface_adapter.vote;

import use_case.vote.VoteOutputBoundary;
import use_case.vote.VoteOutputData;

/**
 * TODO: Presents voting state and winner to the view model.
 */
public class VotePresenter implements VoteOutputBoundary {
    private final VoteViewModel viewModel;

    public VotePresenter(VoteViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    public void present(VoteOutputData outputData) {
        // TODO: Update state and notify
    }

    @Override
    public void presentFailure(String message) {
        // TODO: Update error state and notify
    }
}

