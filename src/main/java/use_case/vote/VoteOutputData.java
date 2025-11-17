package use_case.vote;

import java.util.Map;

/**
 * Output data for voting state and winner.
 *
 * Clean Architecture:
 * - Interactor produces this and passes to Presenter (output boundary)
 */
public class VoteOutputData {
    private final String winnerMovieId;
    private final Map<String, Integer> scores;
    private final int ballotsReceivedCount;
    private final int shortlistSize;

    public VoteOutputData(String winnerMovieId, Map<String, Integer> scores, int ballotsReceivedCount,
            int shortlistSize) {
        this.winnerMovieId = winnerMovieId;
        this.scores = scores;
        this.ballotsReceivedCount = ballotsReceivedCount;
        this.shortlistSize = shortlistSize;
    }

    public String getWinnerMovieId() {
        return winnerMovieId;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public int getBallotsReceivedCount() {
        return ballotsReceivedCount;
    }

    public int getShortlistSize() {
        return shortlistSize;
    }
}
