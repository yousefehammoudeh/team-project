package interface_adapter.vote;

import use_case.vote.VoteInputBoundary;

/**
 * TODO: Submits ranked ballots and requests winner computation (host).
 */
public class VoteController {
    private final VoteInputBoundary interactor;

    public VoteController(VoteInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Methods: submitBallot(rankedIds), computeWinner(hostToken)
}

