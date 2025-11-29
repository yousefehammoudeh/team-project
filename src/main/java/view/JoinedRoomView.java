package view;

import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.joined_room.JoinedRoomViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Participants dashboard (shows room code).
 */
public class JoinedRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    @SuppressWarnings("unused")
    private final JoinedRoomViewModel joinedRoomViewModel;
    private final JLabel roomIdLabel;
    private final JPanel participantsPanel;

    public JoinedRoomView(JoinedRoomViewModel joinedRoomViewModel) {
        this.joinedRoomViewModel = joinedRoomViewModel;
        setLayout(new BorderLayout(10, 10));

        // Room ID
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("< Room ID >", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // Participants Names
        participantsPanel = new JPanel();
        participantsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.CENTER);

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
        // No-op: hook for future controls
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI based on ViewModel changes
        final JoinedRoomState state = (JoinedRoomState) evt.getNewValue();
        setRoomId(state.getRoomcode());
        updateParticipants(state.getParticipants());
    }
}
