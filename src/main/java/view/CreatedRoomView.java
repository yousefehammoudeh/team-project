package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.LeaveRoomController;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import interface_adapter.shortlist.UpdateRoomController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

public class CreatedRoomView extends JPanel implements ActionListener, PropertyChangeListener {

    private final JLabel roomIdLabel;
    private final JLabel hostNameLabel;
    private final JPanel participantsPanel;

    private final JButton searchButton;
    private final JButton shortlistButton;
    private final JButton updateButton;
    private final JButton leaveButton;
//    private final JButton voteButton;

    private ViewManagerModel viewManagerModel;
    private final CreatedRoomViewModel viewModel;
    private SearchViewModel searchViewModel;

    private UpdateRoomController updateRoomController;
    private LeaveRoomController leaveRoomController;

    public CreatedRoomView(CreatedRoomViewModel viewModel) {

        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);

        setLayout(new BorderLayout(10, 10));

        // ROOM ID AND HOST NAME
        final JPanel topPanel = new JPanel();
        topPanel.setLayout(new BoxLayout(topPanel, BoxLayout.Y_AXIS));

        roomIdLabel = new JLabel("Room ID: <unknown>", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        roomIdLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        hostNameLabel = new JLabel("Host: <unknown>", SwingConstants.CENTER);
        hostNameLabel.setFont(new Font("Serif", Font.PLAIN, 18));
        hostNameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);

        topPanel.add(roomIdLabel);
        topPanel.add(Box.createVerticalStrut(5));
        topPanel.add(hostNameLabel);

        add(topPanel, BorderLayout.NORTH);

        // BUTTONS
        final JPanel centerPanel = new JPanel();

        leaveButton = new JButton("Leave");
        leaveButton.addActionListener(this);
        centerPanel.add(leaveButton);

        searchButton = new JButton("\uD83D\uDD0D");  // search icon
        searchButton.addActionListener(this);
        centerPanel.add(searchButton);

        shortlistButton = new JButton("Shortlist");
        shortlistButton.addActionListener(this);
        centerPanel.add(shortlistButton);

        updateButton = new JButton("Refresh");
        updateButton.addActionListener(this);
        centerPanel.add(updateButton);

//        voteButton = new JButton("Vote");
//        voteButton.addActionListener(this);
//        centerPanel.add(voteButton);

        add(centerPanel, BorderLayout.CENTER);

        // PARTICIPANTS
        participantsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.SOUTH);
    }

    private void updateRoomCode(String roomCode) {
        roomIdLabel.setText("Room ID: " + roomCode);
    }

    private void updateHostName(String hostName) {
        hostNameLabel.setText("Host: " + hostName);
    }

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
            if (searchViewModel != null && viewModel.getState() != null) {
                SearchState searchState = searchViewModel.getState();
                if (searchState == null) {
                    searchState = new SearchState();
                    searchViewModel.setState(searchState);
                }
                searchState.setRoomId(viewModel.getState().getRoomCode());
                searchViewModel.firePropertyChanged();
            }
            viewManagerModel.setActiveViewName(ViewManagerModel.SEARCH_VIEW);
        }
        else if (src == shortlistButton) {
            viewManagerModel.setActiveViewName(ViewManagerModel.SHORTLIST_VIEW);
        }
        else if (src == updateButton) {
            updateRoomController.execute();
        }
        else if (src == leaveButton) {
            leaveRoomController.execute();
        }
//        else if (src == voteButton) {
//            viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
//        }

        viewManagerModel.firePropertyChanged();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        CreatedRoomState state = viewModel.getState();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(this, state.getError());
        }
        if (state.getRoomCode() != null && !state.getRoomCode().isEmpty()) {
            updateRoomCode(state.getRoomCode());
        }
        if (state.getHostName() != null && !state.getRoomCode().isEmpty()) {
            updateHostName(state.getHostName());
        }
        if (state.getParticipants() != null) {
            updateParticipants(state.getParticipants());
        }
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public void setUpdateRoomController(UpdateRoomController updateRoomController) {
        this.updateRoomController = updateRoomController;
    }

    public void setLeaveRoomController(LeaveRoomController leaveRoomController) {
        this.leaveRoomController = leaveRoomController;
    }

    public void setSearchViewModel(interface_adapter.search.SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }
}