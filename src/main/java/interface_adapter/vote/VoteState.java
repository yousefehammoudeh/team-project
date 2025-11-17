package interface_adapter.vote;

import java.util.Map;

/**
 * Holds UI state for voting screen.
 *
 * State fields represent the View's observable model. Presenters will update
 * this state and call `firePropertyChanged()` on the ViewModel.
 */
public class VoteState {
    private int ballotsReceivedCount;
    private int shortlistSize;
    private String winnerMovieId;
    private Map<String, Integer> scores;
    private String error;

    public int getBallotsReceivedCount() {
        return ballotsReceivedCount;
    }

    public void setBallotsReceivedCount(int ballotsReceivedCount) {
        this.ballotsReceivedCount = ballotsReceivedCount;
    }

    public int getShortlistSize() {
        return shortlistSize;
    }

    public void setShortlistSize(int shortlistSize) {
        this.shortlistSize = shortlistSize;
    }

    public String getWinnerMovieId() {
        return winnerMovieId;
    }

    public void setWinnerMovieId(String winnerMovieId) {
        this.winnerMovieId = winnerMovieId;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<String, Integer> scores) {
        this.scores = scores;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }
}
