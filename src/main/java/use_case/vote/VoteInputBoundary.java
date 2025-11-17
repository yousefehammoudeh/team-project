package use_case.vote;

/**
 * Interactor API for submitting ballots and computing winner.
 *
 * Clean Architecture roles:
 * - Controller -> calls these methods (input boundary)
 * - Interactor implements these methods and uses a gateway + presenter
 */
public interface VoteInputBoundary {
    /** Submit a ranked ballot for a participant. */
    void submitBallot(VoteInputData inputData);

    /** Compute and announce the winner. Host privilege is checked by hostId. */
    void computeWinner(String hostId);
}
