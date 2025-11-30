package interface_adapter.vote;

import use_case.vote.VoteInputBoundary;
import use_case.vote.VoteInputData;
import java.util.List;

/** Controller for submitting ballots. Winner computation handled separately. */
public class VoteController {
    private final VoteInputBoundary interactor;

    public VoteController(VoteInputBoundary interactor) {
        this.interactor = interactor;
    }

    public void submitBallot(String participantId, List<String> rankedMovieIds) {
        VoteInputData data = new VoteInputData(participantId, rankedMovieIds);
        interactor.submitBallot(data);
    }

    public void computeWinner(String hostId) {
        interactor.computeWinner(hostId);
    }
}
