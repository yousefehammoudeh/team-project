package use_case.vote;

import data_access.room.InMemoryRoomDataAccessObject;
import interface_adapter.vote.VoteController;
import interface_adapter.vote.VotePresenter;
import interface_adapter.vote.VoteViewModel;
import view.VoteView;

import javax.swing.*;
import java.util.Arrays;

public class votedemo {
    public static void main(String[] args) {
        // For now, build a focused Vote demo wiring following Clean Architecture:
        InMemoryRoomDataAccessObject dao = new InMemoryRoomDataAccessObject();

        // Fetch real movies from TMDB for demo
        java.util.List<String> movieIds = new java.util.ArrayList<>();
        java.util.List<String> posterUrls = new java.util.ArrayList<>();

        try {
            dao.createRoom("demoRoom");
        } catch (Exception ex) {
            System.err.println("Failed to create room: " + ex.getMessage());
            return;
        }

        String apiKey = System.getenv("TMDB_API_KEY");
        if (apiKey != null && !apiKey.isBlank()) {
            try {
                data_access.tmdb.TmdbMovieGateway gateway = new data_access.tmdb.TmdbMovieGateway(apiKey, null, null);
                java.util.List<entity.Movie> movies = gateway.search("toy story");

                // Take up to 5 movies for the demo
                int limit = Math.min(5, movies.size());
                for (int i = 0; i < limit; i++) {
                    entity.Movie m = movies.get(i);
                    movieIds.add(m.getId());
                    dao.addMovie(m.getId());

                    String posterPath = m.getPosterPath();
                    if (posterPath != null && !posterPath.isBlank()) {
                        String cleaned = posterPath.startsWith("/") ? posterPath : "/" + posterPath;
                        posterUrls.add("https://image.tmdb.org/t/p/w200" + cleaned);
                    } else {
                        posterUrls.add("");
                    }
                }
            } catch (Exception ex) {
                System.err.println("Failed to fetch movies from TMDB: " + ex.getMessage());
                // Fallback to placeholders
                try {
                    movieIds.addAll(Arrays.asList("A", "B", "C"));
                    posterUrls.addAll(Arrays.asList("", "", ""));
                    dao.addMovie("A");
                    dao.addMovie("B");
                    dao.addMovie("C");
                } catch (Exception e) {
                    System.err.println("Failed to add fallback movies: " + e.getMessage());
                }
            }
        } else {
            System.err.println("TMDB_API_KEY not set - using placeholder data");
            try {
                movieIds.addAll(Arrays.asList("A", "B", "C"));
                posterUrls.addAll(Arrays.asList("", "", ""));
                dao.addMovie("A");
                dao.addMovie("B");
                dao.addMovie("C");
            } catch (Exception e) {
                System.err.println("Failed to add placeholder movies: " + e.getMessage());
            }
        }

        // Use-case layer
        VoteViewModel voteVM = new VoteViewModel();
        VotePresenter votePresenter = new VotePresenter(voteVM);
        VoteInteractor voteInteractor = new VoteInteractor(dao, votePresenter);

        // Interface-adapter layer
        VoteController voteController = new VoteController(voteInteractor);

        // View layer
        VoteView voteView = new VoteView(voteVM);
        voteView.setPosterUrls(posterUrls, movieIds);

        // Wire submit -> controller (controller constructs InputData and calls
        // interactor)
        JLabel statusLabel = new JLabel("Voting as: p1 - Click posters to rank, then submit");
        final String[] currentParticipant = { "p1" }; // Mutable holder for current participant

        voteView.setOnSubmit(rankedIds -> {
            String participantId = currentParticipant[0];
            System.out.println(participantId + " submitting ballot: " + rankedIds);
            voteController.submitBallot(participantId, rankedIds);
            statusLabel.setText("✓ Ballot submitted for " + participantId + ": " + rankedIds);
        });

        // Wire ViewModel updates to the view
        voteVM.addPropertyChangeListener(evt -> {
            interface_adapter.vote.VoteState s = voteVM.getVoteState();
            if (s != null && s.getScores() != null) {
                voteView.displayScores(s.getScores());
            }
            if (s != null && s.getError() != null) {
                JOptionPane.showMessageDialog(voteView, "Error: " + s.getError());
            }
        });

        // Show a simple window for this demo with controls
        JPanel mainPanel = new JPanel(new java.awt.BorderLayout());
        mainPanel.add(voteView, java.awt.BorderLayout.CENTER);

        JPanel controlPanel = new JPanel(new java.awt.FlowLayout());
        JButton computeButton = new JButton("Compute Winner (Host)");
        computeButton.addActionListener(e -> {
            System.out.println("Computing winner...");
            voteController.computeWinner("p1"); // p1 is the host
        });

        JButton toggleButton = new JButton("Switch to p2");
        toggleButton.addActionListener(e -> {
            if (currentParticipant[0].equals("p1")) {
                currentParticipant[0] = "p2";
                toggleButton.setText("Switch to p1");
                statusLabel.setText("Voting as: p2 - Click posters to rank, then submit");
            } else {
                currentParticipant[0] = "p1";
                toggleButton.setText("Switch to p2");
                statusLabel.setText("Voting as: p1 - Click posters to rank, then submit");
            }
        });

        controlPanel.add(statusLabel);
        controlPanel.add(toggleButton);
        controlPanel.add(computeButton);
        mainPanel.add(controlPanel, java.awt.BorderLayout.SOUTH);

        JFrame frame = new JFrame("Vote Demo");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(mainPanel);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
    }
}
