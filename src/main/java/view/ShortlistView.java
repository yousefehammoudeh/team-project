package view;

import interface_adapter.shortlist.AddMovieController;
import interface_adapter.shortlist.RemoveMovieController;
import interface_adapter.shortlist.ShortlistState;
import interface_adapter.shortlist.ShortlistViewModel;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.Random;

/**
 * TODO: Shortlist view (add/remove candidates, lock if host).
 */
public class ShortlistView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Shortlist";
    private final ShortlistViewModel shortlistViewModel;

    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
    private final JList<String> movieIDs;
    private String selectedMovieID;

    private AddMovieController addMovieController; // TODO: for demo and test only
    private RemoveMovieController removeMovieController;

    private final JPanel shortlistPanel;

    public ShortlistView(ShortlistViewModel shortlistViewModel) {
        this.shortlistViewModel = shortlistViewModel;
        this.shortlistViewModel.addPropertyChangeListener(this);

        shortlistPanel = new JPanel();
        shortlistPanel.setLayout(new BoxLayout(shortlistPanel, BoxLayout.Y_AXIS));

        final JButton removeButton = new JButton("Remove");
        shortlistPanel.add(removeButton);
        final JButton lockButton = new JButton("Lock");
        shortlistPanel.add(lockButton);

        movieIDs = new JList<>(movieListModel);
        movieIDs.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        final JScrollPane scrollPane = new JScrollPane(movieIDs);
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

        movieIDs.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                selectedMovieID = movieIDs.getSelectedValue();
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
        // TODO: code above for demo and test only.

        this.add(shortlistPanel);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Handle add/remove/lock actions
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        final ShortlistState state = (ShortlistState) evt.getNewValue();
        movieListModel.clear();
        movieListModel.addAll(state.getMovieIDs());
        // TODO: handle locked
    }

    public void setAddMovieController(AddMovieController addMovieController) {
        this.addMovieController = addMovieController;
    }

    public void setRemoveMovieController(RemoveMovieController removeMovieController) {
        this.removeMovieController = removeMovieController;
    }
}
