package interface_adapter.vote;

import interface_adapter.ViewModel;

/**
 * ViewModel wrapper for `VoteState`.
 *
 * Responsibilities:
 * - Hold the observable `VoteState` instance
 * - Provide convenience methods for views to read/update state
 */
public class VoteViewModel extends ViewModel<VoteState> {
    public VoteViewModel() {
        super();
        VoteState s = new VoteState();
        s.setBallotsReceivedCount(0);
        s.setShortlistSize(0);
        s.setWinnerMovieId(null);
        s.setScores(null);
        s.setError(null);
        this.state = s;
    }

    public VoteState getVoteState() {
        return this.state;
    }
}
