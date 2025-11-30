package use_case.vote;

/** Presenter API for voting outcomes and errors. */
public interface VoteOutputBoundary {
    void present(VoteOutputData outputData);

    void presentFailure(String message);
}
