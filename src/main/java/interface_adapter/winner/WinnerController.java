package interface_adapter.winner;

import data_access.room.RoomDatabase;
import data_access.tmdb.TmdbMovieGateway;
import entity.Ballot;

import javax.swing.ImageIcon;
import java.util.*;

public class WinnerController {
    private final RoomDatabase roomDb;
    private final WinnerPresenter presenter;
    private final TmdbMovieGateway tmdb;

    public WinnerController(RoomDatabase roomDb, WinnerPresenter presenter) {
        this.roomDb = roomDb;
        this.presenter = presenter;
        this.tmdb = new TmdbMovieGateway();
    }

    public void execute() {
        try {
            roomDb.refreshRoom();
            List<Ballot> ballots = roomDb.getBallots();
            List<String> shortlist = roomDb.getShortlist();
            if (shortlist == null || shortlist.isEmpty()) {
                WinnerState ws = new WinnerState();
                ws.setTitle("No movies in shortlist");
                ws.setDetails("Shortlist is empty.");
                presenter.present(ws);
                return;
            }
            // Simple Borda count: n ranks, top gets n points, etc.
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
            for (Map.Entry<String, Integer> e : scores.entrySet()) {
                if (e.getValue() > best) {
                    best = e.getValue();
                    winnerId = e.getKey();
                }
            }
            // Fetch details for winner
            var movie = tmdb.fetchDetails(winnerId, null);
            WinnerState ws = new WinnerState();
            ws.setTitle(movie.getTitle());
            ws.setDetails("Year: " + movie.getYear() + "\nLanguage: " + movie.getLanguage());
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
            ws.setPoster(icon);
            presenter.present(ws);
        } catch (Exception e) {
            WinnerState ws = new WinnerState();
            ws.setTitle("Failed to compute winner");
            ws.setDetails(e.getMessage());
            presenter.present(ws);
        }
    }
}
