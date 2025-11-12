package view;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * TODO: Host dashboard (shows room code, host token, controls for lock, compute winner, apply filters, etc.).
 */
public class HostDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private JLabel roomIdLabel;
    private JTextField searchField;
    private JButton searchButton;
    private JPanel participantsPanel;

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
        searchField = new JTextField(20);
        searchButton = new JButton("\uD83D\uDD0D");
        searchButton.addActionListener(this);
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
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
        // TODO: Dispatch actions to appropriate controllers (lock, compute winner, apply filters)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI based on ViewModel changes
    }
}
