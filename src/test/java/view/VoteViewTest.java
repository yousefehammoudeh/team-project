package view;

import javax.swing.*;
import java.util.Arrays;
import java.util.ArrayList;
import java.util.Random;

/**
 * Manual test harness for VoteView. Similar to other view tests in this
 * project, run this
 * class from your IDE to open a window and interact with the VoteView UI.
 */
public class VoteViewTest {
    public static void main(String[] args) {
        JFrame frame = new JFrame("Vote View - Test");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        VoteView view = new VoteView();

        // Try to use TMDB gateway if API key is available, otherwise fall back to
        // placeholders
        String apiKey = System.getenv("TMDB_API_KEY");
        if (apiKey != null && !apiKey.isBlank()) {
            try {
                data_access.tmdb.TmdbMovieGateway gateway = new data_access.tmdb.TmdbMovieGateway(apiKey, null, null);
                // Pick a very simple random query from a small list to keep the test fun.
                String[] queries = new String[] { "lion", "space", "love", "king", "city", "hero", "dog", "cat", "star",
                        "dream" };
                String query = queries[new Random().nextInt(queries.length)];
                System.out.println("VoteViewTest: using query '" + query + "' for TMDB search");
                java.util.List<entity.Movie> movies = gateway.search(query, null);
                // take up to 5 movies
                if (movies.size() > 5)
                    movies = movies.subList(0, 5);
                java.util.List<String> posterUrls = new ArrayList<>();
                for (entity.Movie m : movies) {
                    String p = m.getPosterPath();
                    if (p == null || p.isBlank())
                        continue;
                    String cleaned = p.startsWith("/") ? p : "/" + p;
                    posterUrls.add(String.format("https://image.tmdb.org/t/p/w200%s", cleaned));
                    if (posterUrls.size() >= 5)
                        break;
                }
                if (posterUrls.isEmpty()) {
                    // no poster paths found -> show placeholders
                    view.setPosterUrls(Arrays.asList("", "", "", "", ""));
                    JOptionPane.showMessageDialog(null, "No posters found for query — showing placeholders.");
                } else {
                    view.setPosterUrls(posterUrls);
                }
            } catch (Exception ex) {
                ex.printStackTrace();
                view.setPosterUrls(Arrays.asList("", "", "", "", ""));
            }
        } else {
            view.setPosterUrls(Arrays.asList("", "", "", "", ""));
            JOptionPane.showMessageDialog(null,
                    "TMDB_API_KEY not set — running with placeholders. Set TMDB_API_KEY in environment to load real posters.");
        }

        frame.add(view);
        frame.setSize(900, 400);
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
