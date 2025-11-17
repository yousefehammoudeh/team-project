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
        if (outputData == null) {
            viewModel.getVoteState().setError("No output");
            viewModel.firePropertyChanged();
            return;
        }
        VoteState s = viewModel.getVoteState();
        s.setBallotsReceivedCount(outputData.getBallotsReceivedCount());
        s.setShortlistSize(outputData.getShortlistSize());
        s.setWinnerMovieId(outputData.getWinnerMovieId());
        s.setScores(outputData.getScores());
        s.setError(null);
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        VoteState s = viewModel.getVoteState();
        s.setError(message == null ? "Unknown error" : message);
        viewModel.firePropertyChanged();
    }
}
