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
    private int participantCount;
    private String winnerMovieId;
    private Map<String, Integer> scores;
    private String error;
    private java.util.List<String> posterUrls;
    private java.util.List<String> movieIds;
    private boolean shortlistLocked;
    private boolean hasVoted;
    private boolean host;

    public int getBallotsReceivedCount() {
        return ballotsReceivedCount;
    }

    public void setBallotsReceivedCount(int ballotsReceivedCount) {
        this.ballotsReceivedCount = ballotsReceivedCount;
    }

    public int getParticipantCount() {
        return participantCount;
    }

    public void setParticipantCount(int participantCount) {
        this.participantCount = participantCount;
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

    public java.util.List<String> getPosterUrls() {
        return posterUrls;
    }

    public void setPosterUrls(java.util.List<String> posterUrls) {
        this.posterUrls = posterUrls;
    }

    public java.util.List<String> getMovieIds() {
        return movieIds;
    }

    public void setMovieIds(java.util.List<String> movieIds) {
        this.movieIds = movieIds;
    }

    public boolean isShortlistLocked() {
        return shortlistLocked;
    }

    public void setShortlistLocked(boolean locked) {
        this.shortlistLocked = locked;
    }

    public boolean hasVoted() {
        return hasVoted;
    }

    public void setHasVoted(boolean hasVoted) {
        this.hasVoted = hasVoted;
    }

    public boolean isHost() {
        return host;
    }

    public void setHost(boolean host) {
        this.host = host;
    }
}
