package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import interface_adapter.shortlist.UpdateRoomController;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Simplified VoteView: only displays poster images and a "Submit Vote" button.
 */
public class VoteView extends JPanel implements java.beans.PropertyChangeListener {
    private final JPanel postersPanel;
    private final JButton submitButton = new JButton("Submit Vote");
    private final JButton computeWinnerButton = new JButton("Compute Winner");
    // mapping of label -> movie id and current ranked selection
    private final Map<JLabel, String> labelToMovieId = new HashMap<>();
    private final List<String> rankedSelection = new ArrayList<>();
    private Consumer<List<String>> onSubmit;
    private Runnable onComputeWinner;
    private UpdateRoomController globalUpdateController;
    private interface_adapter.ViewManagerModel viewManagerModel;

    private interface_adapter.vote.VoteViewModel viewModel;
    private final JLabel statusLabel = new JLabel("Waiting for votes...");
    private final JLabel winnerLabel = new JLabel("Winner: -");
    private final java.awt.event.MouseAdapter refreshClick = new java.awt.event.MouseAdapter() {
        @Override
        public void mouseClicked(java.awt.event.MouseEvent e) {
            if (globalUpdateController != null) {
                globalUpdateController.execute();
            }
        }
    };

    public VoteView(interface_adapter.vote.VoteViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout(8, 8));

        postersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        final JScrollPane scroller = new JScrollPane(postersPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setPreferredSize(new Dimension(880, 320));
        add(scroller, BorderLayout.CENTER);

        final JPanel bottom = new JPanel(new BorderLayout());
        final JPanel infoPanel = new JPanel(new GridLayout(2, 1));
        infoPanel.add(statusLabel);
        infoPanel.add(winnerLabel);
        bottom.add(infoPanel, BorderLayout.WEST);
        submitButton.addActionListener(e -> {
            if (onSubmit != null) {
                onSubmit.accept(new ArrayList<>(rankedSelection));
                // Optionally clear selection after submit
                clearSelection();
            } else {
                JOptionPane.showMessageDialog(this, "Submit Vote pressed (no handler attached)");
            }
        });
        // Right side: submit + compute winner (host-only visible)
        final JPanel rightButtons = new JPanel(new FlowLayout(FlowLayout.RIGHT, 8, 0));
        rightButtons.add(submitButton);
        computeWinnerButton.addActionListener(e -> {
            if (onComputeWinner != null) {
                onComputeWinner.run();
            } else {
                JOptionPane.showMessageDialog(this, "Compute Winner pressed (no handler attached)");
            }
        });
        rightButtons.add(computeWinnerButton);
        bottom.add(rightButtons, BorderLayout.EAST);
        add(bottom, BorderLayout.SOUTH);

        // Allow clicking anywhere in the view to trigger a refresh
        installGlobalRefreshClick(this);
        installGlobalRefreshClick(scroller);
        installGlobalRefreshClick(scroller.getViewport());
        installGlobalRefreshClick(postersPanel);
        installGlobalRefreshClick(bottom);
        installGlobalRefreshClick(infoPanel);
        installGlobalRefreshClick(rightButtons);
        installGlobalRefreshClick(submitButton);
        installGlobalRefreshClick(computeWinnerButton);
    }

    /**
     * Replace the posters panel contents with the provided poster image URLs.
     * Images are loaded synchronously (simple, minimal). If loading fails a
     * placeholder label is shown.
     */
    /**
     * Provide poster URLs and corresponding movie ids. The order of the two lists
     * must match.
     */
    public void setPosterUrls(List<String> posterUrls, List<String> movieIds) {
        postersPanel.removeAll();
        final int posterW = 140;
        final int posterH = 210;

        if (posterUrls == null)
            posterUrls = new ArrayList<>();
        if (movieIds == null)
            movieIds = new ArrayList<>();

        labelToMovieId.clear();
        rankedSelection.clear();

        for (int i = 0; i < posterUrls.size(); i++) {
            String url = posterUrls.get(i);
            String movieId = i < movieIds.size() ? movieIds.get(i) : null;
            JLabel lbl;
            if (url == null || url.isBlank()) {
                lbl = placeholderLabel(posterW, posterH);
            } else {
                try {
                    BufferedImage img = ImageIO.read(URI.create(url).toURL());
                    if (img != null) {
                        Image scaled = img.getScaledInstance(posterW, posterH, Image.SCALE_SMOOTH);
                        lbl = new JLabel(new ImageIcon(scaled));
                    } else {
                        lbl = placeholderLabel(posterW, posterH);
                    }
                } catch (IOException ex) {
                    lbl = placeholderLabel(posterW, posterH);
                }
            }
            // String newText = base.replaceAll(" \\(\\d+ pts\\)", "");
            lbl.setHorizontalTextPosition(SwingConstants.CENTER);
            lbl.setVerticalTextPosition(SwingConstants.BOTTOM);
            // store mapping and create a final reference for listener closure
            final JLabel clickable = lbl;
            if (movieId != null) {
                labelToMovieId.put(clickable, movieId);
            }
            // clickable: toggle selection and maintain rankedSelection
            clickable.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    String id = labelToMovieId.get(clickable);
                    if (id == null)
                        return;
                    if (rankedSelection.contains(id)) {
                        rankedSelection.remove(id);
                    } else {
                        rankedSelection.add(id);
                    }
                    updateSelectionDecorations();
                }
            });
            // Also allow any click on a poster to trigger a refresh update
            clickable.addMouseListener(refreshClick);
            postersPanel.add(clickable);
        }

        revalidate();
        repaint();
    }

    private void updateSelectionDecorations() {
        // apply rank numbers and border highlight
        for (Map.Entry<JLabel, String> e : labelToMovieId.entrySet()) {
            JLabel lbl = e.getKey();
            String id = e.getValue();
            int idx = rankedSelection.indexOf(id);
            if (idx >= 0) {
                lbl.setText("#" + (idx + 1));
                lbl.setBorder(BorderFactory.createLineBorder(Color.BLUE, 3));
            } else {
                lbl.setText("");
                lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            }
        }
        revalidate();
        repaint();
    }

    private void clearSelection() {
        rankedSelection.clear();
        updateSelectionDecorations();
    }

    /**
     * Attach a submit handler that receives the ranked movie id list in selection
     * order.
     * The handler should call the application's `VoteController.submitBallot(...)`.
     */
    public void setOnSubmit(Consumer<List<String>> handler) {
        this.onSubmit = handler;
    }

    /** Attach a handler to compute winner when host presses the button. */
    public void setOnComputeWinner(Runnable handler) {
        this.onComputeWinner = handler;
    }

    /** Inject global update controller so the view can request a refresh. */
    public void setGlobalUpdateController(UpdateRoomController controller) {
        this.globalUpdateController = controller;
    }

    private void installGlobalRefreshClick(java.awt.Component c) {
        if (c == null)
            return;
        c.addMouseListener(refreshClick);
        if (c instanceof java.awt.Container) {
            for (java.awt.Component child : ((java.awt.Container) c).getComponents()) {
                installGlobalRefreshClick(child);
            }
        }
    }

    /** Inject view manager to allow navigation when winner detected. */
    public void setViewManagerModel(interface_adapter.ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    @Override
    public void propertyChange(java.beans.PropertyChangeEvent evt) {
        interface_adapter.vote.VoteState s = (interface_adapter.vote.VoteState) evt.getNewValue();
        if (s == null)
            return;
        
        // Update posters if they're provided
        if (s.getPosterUrls() != null && s.getMovieIds() != null) {
            setPosterUrls(s.getPosterUrls(), s.getMovieIds());
        }
        
        // Update bottom status and controls
        statusLabel.setText("Ballots: " + s.getBallotsReceivedCount() + "/" + s.getParticipantCount());
        if (s.getWinnerMovieId() != null) {
            winnerLabel.setText("Winner: " + s.getWinnerMovieId());
            // Navigate to winner view if manager available
            if (viewManagerModel != null) {
                viewManagerModel.setActiveViewName("Winner");
            }
        } else {
            winnerLabel.setText("Winner: -");
        }
        // Enable submit only if locked AND user hasn't voted yet
        submitButton.setEnabled(s.isShortlistLocked() && !s.hasVoted());
        if (s.hasVoted()) {
            submitButton.setText("Vote Submitted");
        } else {
            submitButton.setText("Submit Vote");
        }

        // Host-only compute winner button: visible only to host and enabled when all
        // ballots submitted
        computeWinnerButton.setVisible(s.isHost());
        boolean allBallotsIn = s.getBallotsReceivedCount() >= s.getParticipantCount() && s.getParticipantCount() > 0;
        computeWinnerButton.setEnabled(s.isHost() && allBallotsIn);
    }

    /** Update UI to display the computed scores for each movie id. */
    public void displayScores(Map<String, Integer> scores) {
        if (scores == null) {
            return;
        }
        for (Map.Entry<JLabel, String> e : labelToMovieId.entrySet()) {
            JLabel lbl = e.getKey();
            String id = e.getValue();
            int pts = scores.getOrDefault(id, 0);
            String base = lbl.getText();
            if (base == null)
                base = "";
            // Replace any existing points suffix like " (X pts)"
            String newText = base.replaceAll(" \\([0-9]+ pts\\)", "");
            if (!newText.isBlank()) {
                newText = newText + " (" + pts + " pts)";
            } else {
                newText = "(" + pts + " pts)";
            }
            lbl.setText(newText);
            lbl.setToolTipText(pts + " pts");
        }
        revalidate();
        repaint();
    }

    private JLabel placeholderLabel(int w, int h) {
        JLabel l = new JLabel("No Image", SwingConstants.CENTER);
        l.setPreferredSize(new Dimension(w, h));
        l.setMaximumSize(new Dimension(w, h));
        return l;
    }
}
