package view;

import interface_adapter.join_room.JoinRoomController;
import interface_adapter.joined_room.JoinedRoomState;
import interface_adapter.ViewManagerModel;
import interface_adapter.joined_room.JoinedRoomViewModel;
import interface_adapter.joined_room.JoinedRoomController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;
import interface_adapter.shortlist.UpdateRoomController;

/**
 * Participants dashboard (shows room code).
 */
public class JoinedRoomView extends JPanel implements ActionListener, PropertyChangeListener {
    @SuppressWarnings("unused")
    private final JoinedRoomViewModel joinedRoomViewModel;
    private ViewManagerModel viewManagerModel;
    private final JLabel roomIdLabel;
    private final JPanel participantsPanel;
    private final JButton leaveRoom;
    private UpdateRoomController globalUpdateController;
    private JoinedRoomController joinedRoomController = null;

    public JoinedRoomView(JoinedRoomViewModel joinedRoomViewModel) {
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

        // Navigation buttons
        final JPanel navigationPanel = new JPanel();
        final JButton shortlistButton = new JButton("Shortlist");
        shortlistButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManagerModel != null) {
                    viewManagerModel.setActiveViewName(ViewManagerModel.SHORTLIST_VIEW);
                }
            }
        });
        navigationPanel.add(shortlistButton);
        add(navigationPanel, BorderLayout.SOUTH);

        // leave room feature
        final JPanel leavePanel = new JPanel();
        leaveRoom = new JButton(JoinedRoomViewModel.EXIT_BUTTON_LABEL);
        leavePanel.add(leaveRoom);
        leaveRoom.addActionListener(
                // This creates an anonymous subclass of ActionListener and instantiates it.
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        // check that the event is join button being clicked
                        if (evt.getSource().equals(leaveRoom)) {
                            // get the state
                            final JoinedRoomState currentState = joinedRoomViewModel.getState();

                            // pass the entered username and room code to the controller
                            // abstraction, calls the interactor inside the controller
                            joinedRoomController.execute(
                                    currentState.getRoomcode());
                        }
                    }
                });

        // Event-driven refresh on user interaction (no background polling)
        registerUserActivityRefresh(this);
        registerUserActivityRefresh(participantsPanel);
        registerUserActivityRefresh(navigationPanel);
        registerUserActivityRefresh(topPanel);
    }

    private void registerUserActivityRefresh(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                triggerRefreshOnActivity();
            }
        });
    }

    private void triggerRefreshOnActivity() {
        if (globalUpdateController != null) {
            globalUpdateController.execute();
        }
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
        setRoomId(state.getRoomcode());
        updateParticipants(state.getParticipants());
        // Auto-navigate to Vote view if room is locked
        if (state.isLocked() && viewManagerModel != null &&
                !ViewManagerModel.VOTE_VIEW.equals(viewManagerModel.getActiveViewName())) {
            viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
        }
    }

    public void setGlobalUpdateController(UpdateRoomController c) {
        this.globalUpdateController = c;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
