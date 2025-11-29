package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CreatedRoomView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JLabel roomIdLabel;
    private final JPanel participantsPanel;

    private final JButton searchButton;
    private final JButton shortlistButton;
    private final JButton voteButton;

    private ViewManagerModel viewManagerModel;
    private final CreatedRoomViewModel viewModel;

    public CreatedRoomView(CreatedRoomViewModel viewModel) {

        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        // --- TOP: Room Code ---
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("Room ID: <unknown>", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // --- CENTER: Buttons ---
        final JPanel centerPanel = new JPanel();

        searchButton = new JButton("\uD83D\uDD0D");  // search icon
        searchButton.addActionListener(this);
        centerPanel.add(searchButton);

        shortlistButton = new JButton("Shortlist");
        shortlistButton.addActionListener(this);
        centerPanel.add(shortlistButton);

        voteButton = new JButton("Vote");
        voteButton.addActionListener(this);
        centerPanel.add(voteButton);

        add(centerPanel, BorderLayout.CENTER);

        // --- BOTTOM: Participants ---
        participantsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.SOUTH);
    }

    /** Update only the room code */
    private void updateRoomCode(String roomCode) {
        roomIdLabel.setText("Room ID: " + roomCode);
    }

    /** Update participant list */
    private void updateParticipants(List<String> names) {
        participantsPanel.removeAll();

        for (String name : names) {
            JLabel label = new JLabel(name);
            label.setFont(new Font("Serif", Font.BOLD, 14));
            participantsPanel.add(label);
        }

        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (viewManagerModel == null) return;

        Object src = e.getSource();

        if (src == searchButton) {
            viewManagerModel.setActiveViewName("search");
        } else if (src == shortlistButton) {
            viewManagerModel.setActiveViewName("shortlist");
        } else if (src == voteButton) {
            viewManagerModel.setActiveViewName("vote");
        }

        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreatedRoomState state = viewModel.getState();

        updateRoomCode(state.getRoomCode());
        updateParticipants(state.getParticipants());
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}