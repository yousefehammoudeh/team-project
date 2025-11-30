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
        super("Vote");
        VoteState s = new VoteState();
        s.setBallotsReceivedCount(0);
        s.setParticipantCount(0);
        s.setWinnerMovieId(null);
        s.setScores(null);
        s.setError(null);
        s.setPosterUrls(java.util.Collections.emptyList());
        s.setMovieIds(java.util.Collections.emptyList());
        s.setShortlistLocked(false);
        s.setHasVoted(false);
        this.state = s;
    }

    public VoteState getVoteState() {
        return this.state;
    }

    // Uses base getViewName()
}
