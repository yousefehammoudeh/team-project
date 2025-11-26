package use_case.vote;

import java.util.List;
import entity.Ballot;

/**
 * Input data for submitting a ballot.
 *
 * Clean Architecture:
 * - Controller constructs this object from user input and passes to Interactor
 */
public class VoteInputData {
    private final String participantId;
    private final List<String> rankedMovieIds;

    public VoteInputData(String participantId, List<String> rankedMovieIds) {
        this.participantId = participantId;
        this.rankedMovieIds = rankedMovieIds;
    }

    public String getParticipantId() {
        return participantId;
    }

    public List<String> getRankedMovieIds() {
        return rankedMovieIds;
    }

    public Ballot toBallot() {
        return new Ballot(participantId, rankedMovieIds);
    }
}
