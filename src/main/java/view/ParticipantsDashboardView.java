package view;

import interface_adapter.joined_room.JoinedRoomController;
import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.joined_room.JoinedRoomViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class ParticipantsDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private JLabel roomIdLabel;
    private JLabel roomTitle;
    private JPanel participantsPanel;
    private final JButton toExit;
    private JoinedRoomController joinedRoomController = null;
    @SuppressWarnings("unused")
    private final JoinedRoomViewModel joinedRoomViewModel;

    public ParticipantsDashboardView(JoinedRoomViewModel joinedRoomViewModel) {
        this.joinedRoomViewModel = joinedRoomViewModel;
        setLayout(new BorderLayout(10, 10));

        // Room ID
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("< Room ID >", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // Room title i.e. waiting for host to select shortlist
        final JPanel titlePanel = new JPanel();
        roomTitle = new JLabel(JoinedRoomViewModel.TITLE_LABEL);
        roomTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        titlePanel.add(roomTitle);
        add(titlePanel, BorderLayout.NORTH);

        // Participants Names
        participantsPanel = new JPanel();
        participantsPanel.setLayout(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.SOUTH);

        final JPanel buttons = new JPanel();
        toExit = new JButton(JoinedRoomViewModel.EXIT_BUTTON_LABEL);
        buttons.add(toExit);
        add(buttons);

        toExit.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent evt) {
                if (evt.getSource().equals(toExit)) {
                    final JoinedRoomState currentState = joinedRoomViewModel.getState();
                    joinedRoomController.execute(currentState.getRoomcode()); // need to get the current user
                }
            }
        });

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
        // TODO: Dispatch actions to appropriate controllers (lock, compute winner,
        // apply filters)
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update UI based on ViewModel changes
        final JoinedRoomState state = (JoinedRoomState) evt.getNewValue();
        setRoomId(state.getRoomcode());
        updateParticipants(state.getParticipants());
    }
}
