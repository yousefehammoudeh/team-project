package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Voting view (ranked selection UI and winner display for host).
 */
public class VoteView extends JPanel implements ActionListener, PropertyChangeListener {
    private final DefaultListModel<String> model = new DefaultListModel<>();
    private final JList<String> list = new JList<>(model);
    private final JButton submitButton = new JButton("Submit Vote");
    private final JButton showWinnerButton = new JButton("Show Winner");
    private ViewManagerModel viewManagerModel;

    // Poster buttons and rank labels (one panel per candidate)
    private final java.util.List<JButton> posterButtons = new java.util.ArrayList<>();
    private final java.util.List<JLabel> rankLabels = new java.util.ArrayList<>();
    // The ranking order (candidate strings) in the order the user pressed posters
    private final java.util.List<String> rankings = new java.util.ArrayList<>();

    public VoteView() {
        setLayout(new BorderLayout(8, 8));
        list.setSelectionMode(ListSelectionModel.MULTIPLE_INTERVAL_SELECTION);
        // center will hold the poster buttons (populated by setCandidates)
        final JPanel center = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        center.setName("candidatesPanel");
        add(center, BorderLayout.CENTER);
        final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton.addActionListener(this);
        showWinnerButton.addActionListener(this);
        bottom.add(submitButton);
        bottom.add(showWinnerButton);
        add(bottom, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == submitButton) {
            // In a real app this would submit the ranked ballot to VoteController
            JOptionPane.showMessageDialog(this, "Submitted ballot (scaffold): " + rankings);
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("HostDashboard");
        } else if (e.getSource() == showWinnerButton) {
            // Requesting winner is out of scope for scaffolding — show placeholder
            JOptionPane.showMessageDialog(this, "Winner: (placeholder)");
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("HostDashboard");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI with voting progress and winner
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    /** Helper to set the list of candidate movie ids or titles to rank. */
    public void setCandidates(java.util.List<String> candidates) {
        // Clear previous
        model.clear();
        posterButtons.clear();
        rankLabels.clear();
        rankings.clear();

        // Find the center panel we added in constructor
        Component centerComp = null;
        for (Component c : getComponents()) {
            if (c instanceof JPanel && "candidatesPanel".equals(c.getName())) {
                centerComp = c;
                break;
            }
        }
        final JPanel center;
        if (centerComp == null) {
            center = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
            center.setName("candidatesPanel");
            add(center, BorderLayout.CENTER);
        } else {
            center = (JPanel) centerComp;
            center.removeAll();
        }

        for (String c : candidates) {
            model.addElement(c);
            final JPanel panel = new JPanel();
            panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
            final JButton poster = new JButton(c);
            poster.setName("poster-" + c);
            poster.setAlignmentX(Component.CENTER_ALIGNMENT);
            final JLabel rankLabel = new JLabel("");
            rankLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
            poster.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    // If this candidate wasn't ranked yet, append to rankings and show rank
                    if (!rankings.contains(c)) {
                        rankings.add(c);
                        rankLabel.setText(Integer.toString(rankings.size()));
                    }
                }
            });
            posterButtons.add(poster);
            rankLabels.add(rankLabel);
            panel.add(poster);
            panel.add(Box.createVerticalStrut(4));
            panel.add(rankLabel);
            center.add(panel);
        }

        // revalidate to show changes
        revalidate();
        repaint();
    }

    /** Expose poster buttons so tests can trigger clicks. */
    public java.util.List<JButton> getPosterButtons() {
        return java.util.Collections.unmodifiableList(posterButtons);
    }

    /** Expose rank labels for assertions in tests. */
    public java.util.List<JLabel> getRankLabels() {
        return java.util.Collections.unmodifiableList(rankLabels);
    }

    /** Return the current ranking order (first pressed is index 0). */
    public java.util.List<String> getRankings() {
        return new java.util.ArrayList<>(rankings);
    }
}
