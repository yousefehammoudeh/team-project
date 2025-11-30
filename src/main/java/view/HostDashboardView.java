package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.host_dashboard.HostDashboardState;
import interface_adapter.shortlist.UpdateRoomController;
import interface_adapter.host_dashboard.HostDashboardViewModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

/**
 * Host dashboard (shows room code, navigation, and participants).
 */
public class HostDashboardView extends JPanel implements ActionListener, PropertyChangeListener {
    private final JLabel roomIdLabel;
    private final JButton searchButton;
    private final JButton shortlistButton;
    private final JPanel participantsPanel;
    private ViewManagerModel viewManagerModel;
    private final HostDashboardViewModel viewModel;
    private UpdateRoomController globalUpdateController;
    private interface_adapter.search.SearchViewModel searchViewModel;

    public HostDashboardView(HostDashboardViewModel viewModel) {
        this.viewModel = viewModel;
        this.viewModel.addPropertyChangeListener(this);
        setLayout(new BorderLayout(10, 10));

        // Room ID
        final JPanel topPanel = new JPanel();
        roomIdLabel = new JLabel("< Room ID >", SwingConstants.CENTER);
        roomIdLabel.setFont(new Font("Serif", Font.BOLD, 20));
        topPanel.add(roomIdLabel);
        add(topPanel, BorderLayout.NORTH);

        // Navigation buttons
        final JPanel navigationPanel = new JPanel();
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        navigationPanel.add(searchButton);
        shortlistButton = new JButton("Shortlist");
        shortlistButton.addActionListener(this);
        navigationPanel.add(shortlistButton);
        add(navigationPanel, BorderLayout.CENTER);

        // Participants Names along the bottom
        participantsPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        add(participantsPanel, BorderLayout.SOUTH);

        // Event-driven refresh (no background polling)
        registerUserActivityRefresh(this);
        registerUserActivityRefresh(participantsPanel);
        registerUserActivityRefresh(navigationPanel);
        registerUserActivityRefresh(topPanel);
    }

    // No debounce per requirement

    private void registerUserActivityRefresh(JComponent component) {
        component.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                triggerRefreshOnActivity();
            }
        });
    }

    private void triggerRefreshOnActivity() {
        if (globalUpdateController != null)
            globalUpdateController.execute();
    }

    // Backwards-compatible no-arg constructor for existing tests
    public HostDashboardView() {
        this(new HostDashboardViewModel());
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
            // Update SearchViewModel with current room ID before navigating
            if (searchViewModel != null && viewModel.getState() != null) {
                interface_adapter.search.SearchState searchState = searchViewModel.getState();
                if (searchState == null) {
                    searchState = new interface_adapter.search.SearchState();
                    searchViewModel.setState(searchState);
                }
                searchState.setRoomId(viewModel.getState().getRoomId());
                searchViewModel.firePropertyChanged();
            }
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("Search");
        } else if (src == shortlistButton) {
            if (viewManagerModel != null)
                viewManagerModel.setActiveViewName("Shortlist");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        HostDashboardState state = (HostDashboardState) evt.getNewValue();
        if (state == null)
            return;
        if (state.getRoomId() != null) {
            setRoomId(state.getRoomId());
        }
        if (state.getParticipants() != null) {
            updateParticipants(state.getParticipants());
        }
        // Auto-navigate to Vote view if room is locked
        if (state.isLocked() && viewManagerModel != null &&
                !ViewManagerModel.VOTE_VIEW.equals(viewManagerModel.getActiveViewName())) {
            viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
        }
        participantsPanel.revalidate();
        participantsPanel.repaint();
    }

    /**
     * Optional wiring: allow composition code to provide the ViewManagerModel so
     * views can request navigation.
     */
    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public void setGlobalUpdateController(UpdateRoomController c) {
        this.globalUpdateController = c;
    }

    public void setSearchViewModel(interface_adapter.search.SearchViewModel searchViewModel) {
        this.searchViewModel = searchViewModel;
    }
}
