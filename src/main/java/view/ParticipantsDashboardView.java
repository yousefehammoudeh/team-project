package view;

import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.ViewManagerModel;
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
public class ParticipantsDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    @SuppressWarnings("unused")
    private final JoinedRoomViewModel joinedRoomViewModel;
    private ViewManagerModel viewManagerModel;
    private final JLabel roomIdLabel;
    private final JPanel participantsPanel;
    private interface_adapter.host_dashboard.ParticipantsRefreshController participantsRefreshController;

    public ParticipantsDashboardView(JoinedRoomViewModel joinedRoomViewModel) {
        this.joinedRoomViewModel = joinedRoomViewModel;
        this.joinedRoomViewModel.addPropertyChangeListener(this);
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

        // Optional: background refresh for participants
        new Thread(() -> {
            try {
                while (true) {
                    if (participantsRefreshController != null) {
                        participantsRefreshController.execute();
                    }
                    Thread.sleep(5000);
                }
            } catch (InterruptedException ignored) {
            }
        }).start();
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
        final JoinedRoomState state = (JoinedRoomState) evt.getNewValue();
        System.out.println("[ParticipantsDashboard] propertyChange: locked=" + state.isLocked() +
                ", viewManagerModel=" + (viewManagerModel != null) +
                ", currentView=" + (viewManagerModel != null ? viewManagerModel.getActiveViewName() : "null"));
        setRoomId(state.getRoomcode());
        updateParticipants(state.getParticipants());
        // Auto-navigate to Vote view if room is locked
        if (state.isLocked() && viewManagerModel != null &&
                !ViewManagerModel.VOTE_VIEW.equals(viewManagerModel.getActiveViewName())) {
            System.out.println("[ParticipantsDashboard] NAVIGATING TO VOTE NOW!");
            viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
            System.out
                    .println("[ParticipantsDashboard] After navigation, view=" + viewManagerModel.getActiveViewName());
        } else {
            System.out.println("[ParticipantsDashboard] NOT navigating: locked=" + state.isLocked() +
                    ", alreadyOnVote=" + ViewManagerModel.VOTE_VIEW
                            .equals(viewManagerModel != null ? viewManagerModel.getActiveViewName() : null));
        }
    }

    public void setParticipantsRefreshController(interface_adapter.host_dashboard.ParticipantsRefreshController c) {
        this.participantsRefreshController = c;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
