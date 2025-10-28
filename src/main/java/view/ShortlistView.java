package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Shortlist view (add/remove candidates, lock if host).
 */
public class ShortlistView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire actions to ShortlistController

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Handle add/remove/lock actions
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Refresh shortlist UI
    }
}
