package interface_adapter.vote;

import use_case.vote.VoteOutputBoundary;
import use_case.vote.VoteOutputData;

/** Presents voting state updates and errors to the view model. */
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
        s.setParticipantCount(outputData.getParticipantCount());
        s.setWinnerMovieId(outputData.getWinnerMovieId());
        s.setScores(outputData.getScores());
        s.setError(null);
        // Set hasVoted from outputData (user-specific, not global ballot count)
        s.setHasVoted(outputData.isCurrentUserHasVoted());
        viewModel.firePropertyChanged();
    }

    @Override
    public void presentFailure(String message) {
        VoteState s = viewModel.getVoteState();
        s.setError(message == null ? "Unknown error" : message);
        viewModel.firePropertyChanged();
    }
}
