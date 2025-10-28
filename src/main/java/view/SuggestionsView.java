package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Suggestions view (recommend sequels or similar movies).
 */
public class SuggestionsView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire suggestion request to SuggestionsController

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Trigger suggestions fetch
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update list of suggestions
    }
}
