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
 * Participants dashboard - shows room code and participants list.
 * Participants wait here until host locks the shortlist, then automatically
 * navigate to Vote view.
 * TODO: Wire up with JoinRoomViewModel to get real room data
 * TODO: Add property change listener to auto-navigate to Vote when shortlist is
 * locked
 * TODO: Update participants list in real-time when new participants join
 */
public class ParticipantsDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "ParticipantsDashboard";
    private JLabel roomIdLabel;
    private JPanel participantsPanel;
    private JLabel statusLabel;
    @SuppressWarnings("unused")
    private ViewManagerModel viewManagerModel;

    public ParticipantsDashboardView() {
        setLayout(new BorderLayout(10, 10));

        // Room ID
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("< Room ID >", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // Status message in center
        final JPanel centerPanel = new JPanel();
        statusLabel = new JLabel("Waiting for host to lock shortlist...", SwingConstants.CENTER);
        statusLabel.setFont(new Font("SansSerif", Font.ITALIC, 14));
        statusLabel.setForeground(Color.GRAY);
        centerPanel.add(statusLabel);
        add(centerPanel, BorderLayout.CENTER);

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
        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // No buttons to handle - participants wait for host to lock shortlist
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Listen for "shortlistLocked" property change from ViewModel
        // TODO: When shortlist is locked, automatically navigate to Vote view:
        // if (viewManagerModel != null) {
        // viewManagerModel.setActiveViewName("Vote");
        // }
        // TODO: Update participants list when new participants join
        // TODO: Update status label to show current room state
    }

    /**
     * Sets the ViewManagerModel for navigation.
     */
    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public String getViewName() {
        return viewName;
    }
}
