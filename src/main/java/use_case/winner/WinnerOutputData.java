package use_case.winner;

import javax.swing.ImageIcon;
import java.util.Map;

public class WinnerOutputData {
    private final String winnerMovieId;
    private final String title;
    private final String details;
    private final ImageIcon poster;
    private final Map<String, Integer> scores;

    public WinnerOutputData(String winnerMovieId, String title, String details, ImageIcon poster,
            Map<String, Integer> scores) {
        this.winnerMovieId = winnerMovieId;
        this.title = title;
        this.details = details;
        this.poster = poster;
        this.scores = scores;
    }

    public String getWinnerMovieId() {
        return winnerMovieId;
    }

    public String getTitle() {
        return title;
    }

    public String getDetails() {
        return details;
    }

    public ImageIcon getPoster() {
        return poster;
    }

    public Map<String, Integer> getScores() {
        return scores;
    }
}
