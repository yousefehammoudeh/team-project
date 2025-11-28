package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Shortlist view: add/remove candidates, lock if host.
 */
public class ShortlistView extends JPanel implements PropertyChangeListener {
    private final static int UPDATE_INTERVAL = 20;

    private final String viewName = "Shortlist";
    private final ShortlistViewModel shortlistViewModel;

    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
    private final JList<String> shortlist;
    private String selectedMovieID;
    private int lastShortlistSize = 0;
    private long lastUpdateTime = 0;
    private long lastActionTime = 0;
    private boolean lockInFlight = false;
    private JButton lockButtonRef;

    private AddMovieController addMovieController;
    private RemoveMovieController removeMovieController;
    private UpdateRoomController updateRoomController;
    private ToggleLockRoomController toggleLockRoomController;

    private final JPanel shortlistPanel = new JPanel();
    private final JLabel lockedText = new JLabel();

    private ViewManagerModel viewManagerModel;

    public ShortlistView(ViewManagerModel viewManagerModel, ShortlistViewModel shortlistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.shortlistViewModel = shortlistViewModel;
        this.shortlistViewModel.addPropertyChangeListener(this);

        shortlistPanel.setLayout(new BorderLayout(10, 10));

        // Top aligned controls row
        final JPanel controlsRow = new JPanel(new FlowLayout(FlowLayout.CENTER, 12, 8));
        final JButton searchButton = new JButton("Search");
        searchButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManagerModel != null)
                    viewManagerModel.setActiveViewName(ViewManagerModel.SEARCH_VIEW);
            }
        });
        controlsRow.add(searchButton);

        final JButton removeButton = new JButton("Remove");
        controlsRow.add(removeButton);

        final JButton lockButton = new JButton("Lock");
        this.lockButtonRef = lockButton;
        controlsRow.add(lockButton);

        shortlistPanel.add(controlsRow, BorderLayout.NORTH);

        shortlist = new JList<>(movieListModel);
        shortlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        shortlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMovieID = shortlist.getSelectedValue();
            }
        });
        final JScrollPane scrollPane = new JScrollPane(shortlist);
        shortlistPanel.add(scrollPane, BorderLayout.CENTER);

        final JButton removeButton = new JButton("Remove");
        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                // Debounce: prevent action if less than 1 second since last action
                if (currentTime - lastActionTime < 1000) {
                    return;
                }
                String selectedDisplay = shortlist.getSelectedValue();
                if (selectedDisplay != null) {
                    // Extract the movie ID from "Movie ID: xxx" format
                    String movieId = selectedDisplay.replace("Movie ID: ", "");
                    removeMovieController.execute(movieId);
                    selectedMovieID = null;
                    lastActionTime = currentTime;
                }
            }
        });
        shortlistPanel.add(removeButton);

        final JButton lockButton = new JButton("Lock");
        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                long currentTime = System.currentTimeMillis();
                // Debounce: prevent action if less than 1500ms since last action
                if (currentTime - lastActionTime < 1500 || lockInFlight) {
                    return;
                }
                // Mark in-flight and disable button to prevent rapid re-clicks
                lockInFlight = true;
                if (lockButtonRef != null) {
                    lockButtonRef.setEnabled(false);
                }
                toggleLockRoomController.execute();
                lastActionTime = currentTime;
            }
        });

        shortlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMovieID = shortlist.getSelectedValue();
            }
        });

        // Vote button intentionally removed for now during workflow integration

        lockedText.setText("Not Locked");
        // Place lock status away from center so it doesn't replace the list
        shortlistPanel.add(lockedText, BorderLayout.SOUTH);
        // End controls

        this.add(shortlistPanel);

        new Thread(() -> {
            while (true) {
                try {
                    long currentTime = System.currentTimeMillis();
                    // Only update if we're viewing this screen and no recent user action
                    if (getViewName().equals(viewManagerModel.getActiveViewName()) &&
                    // Skip auto-refresh for 30s after a user action to avoid rate limits
                            (currentTime - lastActionTime) > 30000) {
                        updateRoomController.execute();
                    }
                    Thread.sleep(UPDATE_INTERVAL * 1000);
                } catch (

                InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ShortlistState state = (ShortlistState) evt.getNewValue();
        if (state.getError() != null) {
            // Suppress error popups to reduce noise/rate-limit dialogs
            if (lockButtonRef != null) {
                lockButtonRef.setEnabled(true);
            }
            lockInFlight = false;
        } else {
            String selectedID = shortlist.getSelectedValue();
            int newSize = state.getShortlist().size();
            long currentTime = System.currentTimeMillis();

            // Remove confirmation to avoid rate-limit popups and noise

            movieListModel.clear();
            // Format movie entries for display (Movie ID: xxx)
            for (String movieId : state.getShortlist()) {
                movieListModel.addElement("Movie ID: " + movieId);
            }
            shortlist.setSelectedValue(selectedID, true);

            // no-op: confirmation removed

            lastShortlistSize = newSize;
            lastUpdateTime = currentTime;

            if (state.isLocked()) {
                lockedText.setText("Locked");
                // Lock succeeded; re-enable button and clear in-flight
                if (lockButtonRef != null) {
                    lockButtonRef.setEnabled(true);
                }
                lockInFlight = false;
                // Transport to Vote view when locked
                if (viewManagerModel != null &&
                        !ViewManagerModel.VOTE_VIEW.equals(viewManagerModel.getActiveViewName())) {
                    viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
                }
            } else {
                lockedText.setText("Not Locked");
                // Unlock state also clears in-flight and re-enables button
                if (lockButtonRef != null) {
                    lockButtonRef.setEnabled(true);
                }
                lockInFlight = false;
            }
        }
    }

    public void setAddMovieController(AddMovieController addMovieController) {
        this.addMovieController = addMovieController;
    }

    public void setRemoveMovieController(RemoveMovieController removeMovieController) {
        this.removeMovieController = removeMovieController;
    }

    public void setUpdateRoomController(UpdateRoomController updateRoomController) {
        this.updateRoomController = updateRoomController;
    }

    public void setToggleLockRoomController(ToggleLockRoomController toggleLockRoomController) {
        this.toggleLockRoomController = toggleLockRoomController;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public String getViewName() {
        return viewName;
    }
}
