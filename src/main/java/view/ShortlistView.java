package view;

import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * TODO: Shortlist view (add/remove candidates, lock if host).
 */
public class ShortlistView extends JPanel implements ActionListener, PropertyChangeListener {
    private final static int UPDATE_INTERVAL = 5;

    private final String viewName = "Shortlist";
    private final ShortlistViewModel shortlistViewModel;

    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
    private final JList<String> shortlist;
    private String selectedMovieID;

    private AddMovieController addMovieController;
    private RemoveMovieController removeMovieController;
    private UpdateRoomController updateRoomController;

    private final JPanel shortlistPanel;
    private ViewManagerModel viewManagerModel;

    public ShortlistView(ViewManagerModel viewManagerModel, ShortlistViewModel shortlistViewModel) {
        this.viewManagerModel = viewManagerModel;
        this.shortlistViewModel = shortlistViewModel;
        this.shortlistViewModel.addPropertyChangeListener(this);

        shortlistPanel = new JPanel();
        shortlistPanel.setLayout(new BoxLayout(shortlistPanel, BoxLayout.Y_AXIS));

        final JButton removeButton = new JButton("Remove");
        shortlistPanel.add(removeButton);
        final JButton lockButton = new JButton("Lock");
        shortlistPanel.add(lockButton);

        shortlist = new JList<>(movieListModel);
        shortlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane scrollPane = new JScrollPane(shortlist);
        shortlistPanel.add(scrollPane);

        removeButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeMovieController.execute(selectedMovieID);
                selectedMovieID = null;
            }
        });

        lockButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Lock");
            }
        });

        shortlist.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMovieID = shortlist.getSelectedValue();
            }
        });

        // TODO: code below for demo and test only.
        final Random random = new Random();
        final JButton addButton = new JButton("Add");
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovieController.execute(Integer.toString(random.nextInt(100)));
            }
        });
        shortlistPanel.add(addButton);
        // Vote button to proceed to voting view
        final JButton voteButton = new JButton("Vote");
        voteButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (viewManagerModel != null)
                    viewManagerModel.setActiveViewName("Vote");
                else
                    JOptionPane.showMessageDialog(null, "Proceed to vote (scaffold)");
            }
        });
        shortlistPanel.add(voteButton);

        // TODO: code above for demo and test only.

        this.add(shortlistPanel);

        new Thread(() -> {
            while (true) {
                try {
                    if (getViewName().equals(viewManagerModel.getActiveViewName())) {
                        updateRoomController.execute();
                    }
                    Thread.sleep(UPDATE_INTERVAL * 1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }).start();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Handle add/remove/lock actions
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ShortlistState state = (ShortlistState) evt.getNewValue();
        if (state.getError() != null) {
            JOptionPane.showMessageDialog(null, state.getError());
        }
        else {
            String selectedID = shortlist.getSelectedValue();
            movieListModel.clear();
            movieListModel.addAll(state.getShortlist());
            shortlist.setSelectedValue(selectedID, true);
        }
    }

    public void setAddMovieController(AddMovieController addMovieController) {
        this.addMovieController = addMovieController;
    }

    public void setRemoveMovieController(RemoveMovieController removeMovieController) {
        this.removeMovieController = removeMovieController;
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public void setUpdateRoomController(UpdateRoomController updateRoomController) {
        this.updateRoomController = updateRoomController;
    }

    public String getViewName() {
        return viewName;
    }
}
