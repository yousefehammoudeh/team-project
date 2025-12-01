package use_case.winner;

import data_access.note_database.DataAccessException;
import data_access.room.RoomDatabase;
import entity.Ballot;
import entity.Movie;

import javax.swing.ImageIcon;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WinnerInteractor implements WinnerInputBoundary {
    private final RoomDatabase roomDb;
    private final WinnerOutputBoundary presenter;
    private final WinnerMovieDataAccessInterface movieGateway;

    public WinnerInteractor(RoomDatabase roomDb, WinnerOutputBoundary presenter,
            WinnerMovieDataAccessInterface movieGateway) {
        this.roomDb = roomDb;
        this.presenter = presenter;
        this.movieGateway = movieGateway;
    }

    @Override
    public void computeWinner() {
        try {
            roomDb.refreshRoom();
            List<Ballot> ballots = roomDb.getBallots();
            List<String> shortlist = roomDb.getShortlist();
            if (shortlist == null || shortlist.isEmpty()) {
                presenter.presentFailure("Shortlist empty; cannot compute winner");
                return;
            }
            Map<String, Integer> scores = new HashMap<>();
            for (String m : shortlist)
                scores.put(m, 0);
            int n = shortlist.size();
            for (Ballot b : ballots) {
                List<String> ranked = b.getRankedMovieIds();
                for (int i = 0; i < ranked.size(); i++) {
                    String mid = ranked.get(i);
                    if (scores.containsKey(mid)) {
                        scores.put(mid, scores.get(mid) + (n - i));
                    }
                }
            }
            String winnerId = shortlist.get(0);
            int best = -1;
            // Iterate using shortlist order so ties favor earlier movies deterministically
            for (String movieId : shortlist) {
                Integer score = scores.get(movieId);
                if (score != null && score > best) {
                    best = score;
                    winnerId = movieId;
                }
            }
            var movie = movieGateway.fetchDetails(winnerId, null);
            ImageIcon icon = null;
            String posterPath = movie.getPosterPath();
            if (posterPath != null && !posterPath.isBlank()) {
                try {
                    String cleaned = posterPath.startsWith("/") ? posterPath : "/" + posterPath;
                    java.net.URI uri = java.net.URI.create("https://image.tmdb.org/t/p/w300" + cleaned);
                    icon = new ImageIcon(uri.toURL());
                } catch (Exception ignored) {
                }
            }
            // Persist winner id so other views can observe and navigate
            roomDb.setWinnerMovieId(winnerId);
            WinnerOutputData out = new WinnerOutputData(winnerId, movie.getTitle(),
                    buildDetails(movie), icon, scores);
            presenter.present(out);
        } catch (DataAccessException | IOException e) {
            presenter.presentFailure(e.getMessage());
        }
    }

    @Override
    public void displayWinner() {
        try {
            roomDb.refreshRoom();
            String winnerId = roomDb.getWinnerMovieId();
            if (winnerId == null || winnerId.isBlank()) {
                presenter.presentFailure("No winner has been computed yet");
                return;
            }

            var movie = movieGateway.fetchDetails(winnerId, null);
            ImageIcon icon = null;
            String posterPath = movie.getPosterPath();
            if (posterPath != null && !posterPath.isBlank()) {
                try {
                    String cleaned = posterPath.startsWith("/") ? posterPath : "/" + posterPath;
                    java.net.URI uri = java.net.URI.create("https://image.tmdb.org/t/p/w300" + cleaned);
                    icon = new ImageIcon(uri.toURL());
                } catch (Exception ignored) {
                }
            }

            WinnerOutputData out = new WinnerOutputData(winnerId, movie.getTitle(),
                    buildDetails(movie), icon, null);
            presenter.present(out);
        } catch (DataAccessException | IOException e) {
            presenter.presentFailure(e.getMessage());
        }
    }

    private String buildDetails(Movie movie) {
        if (movie == null)
            return "";
        String year = movie.getYear() == null ? "" : movie.getYear();
        String lang = movie.getLanguage() == null ? "" : movie.getLanguage();
        String genres = (movie.getGenres() == null || movie.getGenres().isEmpty())
                ? "-"
                : String.join(", ", movie.getGenres());
        // TMDB vote_average is 0-10; show with one decimal
        String rating = String.format("%.1f/10", movie.getRating());
        return "Year: " + year + "\n" +
                "Language: " + lang + "\n" +
                "Genres: " + genres + "\n" +
                "Rating: " + rating;
    }
}
