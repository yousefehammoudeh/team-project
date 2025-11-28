package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Host dashboard (shows room code, controls for lock, compute
 * winner, apply filters, etc.).
 */
public class HostDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JLabel roomIdLabel;
    private final JButton searchButton;
    private final JButton shortlistButton;
    private final JButton voteButton;
    private final JPanel participantsPanel;
    private ViewManagerModel viewManagerModel;

    public HostDashboardView() {
        setLayout(new BorderLayout(10, 10));

        // Room ID
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("< Room ID >", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // Search Bar
        final JPanel searchPanel = new JPanel();
        JTextField searchField = new JTextField(20);
        searchButton = new JButton("\uD83D\uDD0D");
        searchButton.addActionListener(this);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        shortlistButton = new JButton("Shortlist");
        shortlistButton.addActionListener(this);
        searchPanel.add(shortlistButton);
        voteButton = new JButton("Vote");
        voteButton.addActionListener(this);
        searchPanel.add(voteButton);
        add(searchPanel, BorderLayout.CENTER);

        // Participants Names
        participantsPanel = new JPanel();
        participantsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.SOUTH);
    }

    public void setRoomId(String id) {
        roomIdLabel.setText("Room ID: " + id);
    }

    public void updateParticipants(List<String> names) {
        participantsPanel.removeAll();
        for (String name : names) {
            final JLabel nameLabel = new JLabel(name);
            nameLabel.setFont(new Font("Serif", Font.BOLD, 14));
            participantsPanel.add(nameLabel);
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        Object src = e.getSource();
        if (src == searchButton) {
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("Search");
        } else if (src == shortlistButton) {
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("Shortlist");
        } else if (src == voteButton) {
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("Vote");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI based on ViewModel changes
    }

    /**
     * Optional wiring: allow composition code to provide the ViewManagerModel so
     * views can request navigation.
     */
    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
