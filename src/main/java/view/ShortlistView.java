package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;

import javax.swing.*;
import java.awt.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Shortlist view: add/remove candidates, lock if host.
 */
public class ShortlistView extends JPanel implements PropertyChangeListener {
    private final String viewName = "Shortlist";
    private final ShortlistViewModel shortlistViewModel;

    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
    private final JList<String> shortlist;

    @SuppressWarnings("unused")
    private AddMovieController addMovieController;
    private RemoveMovieController removeMovieController;
    private UpdateRoomController updateRoomController;
    private ToggleLockRoomController toggleLockRoomController;

    private final JPanel shortlistPanel = new JPanel();
    private final JLabel lockedText = new JLabel();
    private JButton voteButton;

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
        controlsRow.add(lockButton);

        voteButton = new JButton("Vote");
        voteButton.setVisible(false); // Initially hidden, shown when locked
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManagerModel != null) {
                    viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
                }
            }
        });
        controlsRow.add(voteButton);

        shortlistPanel.add(controlsRow, BorderLayout.NORTH);

        shortlist = new JList<>(movieListModel);
        shortlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane scrollPane = new JScrollPane(shortlist);
        shortlistPanel.add(scrollPane, BorderLayout.CENTER);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String selectedDisplay = shortlist.getSelectedValue();
                if (selectedDisplay != null) {
                    String movieId = selectedDisplay.replace("Movie ID: ", "");
                    removeMovieController.execute(movieId);
                }
            }
        });

        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                toggleLockRoomController.execute();
            }
        });

        shortlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                // No-op now
            }
        });

        // Vote button intentionally removed for now during workflow integration

        lockedText.setText("Not Locked");
        // Place lock status away from center so it doesn't replace the list
        shortlistPanel.add(lockedText, BorderLayout.SOUTH);
        // End controls

        this.add(shortlistPanel);
        // Event-driven refresh on user activity (no background polling)
        registerUserActivityRefresh(this);
        registerUserActivityRefresh(shortlist);
        registerUserActivityRefresh(controlsRow);
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
        if (updateRoomController != null) {
            updateRoomController.execute();
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ShortlistState state = (ShortlistState) evt.getNewValue();
        if (state.getError() != null) {
            // No popup
        } else {
            String selectedID = shortlist.getSelectedValue();
            movieListModel.clear();
            for (String movieId : state.getShortlist()) {
                movieListModel.addElement("Movie ID: " + movieId);
            }
            shortlist.setSelectedValue(selectedID, true);

            if (state.isLocked()) {
                lockedText.setText("Locked");
                if (voteButton != null) {
                    voteButton.setVisible(true);
                }
                if (viewManagerModel != null &&
                        !ViewManagerModel.VOTE_VIEW.equals(viewManagerModel.getActiveViewName())) {
                    viewManagerModel.setActiveViewName(ViewManagerModel.VOTE_VIEW);
                }
            } else {
                lockedText.setText("Not Locked");
                if (voteButton != null) {
                    voteButton.setVisible(false);
                }
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
