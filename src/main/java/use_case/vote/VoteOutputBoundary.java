package use_case.vote;

/**
 * TODO: Presenter API for voting outcomes.
 */
public interface VoteOutputBoundary {
    void present(VoteOutputData outputData);
    void presentFailure(String message);
}

