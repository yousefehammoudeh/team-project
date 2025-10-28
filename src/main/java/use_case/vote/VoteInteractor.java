package use_case.vote;

/**
 * TODO: Implements vote submission and winner computation.
 */
public class VoteInteractor implements VoteInputBoundary {
    private final VoteUserDataAccessInterface gateway;
    private final VoteOutputBoundary presenter;

    public VoteInteractor(VoteUserDataAccessInterface gateway,
                          VoteOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    // TODO: Implement submitBallot and computeWinner
}

