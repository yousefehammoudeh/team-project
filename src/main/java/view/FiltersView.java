package view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Filters view (host sets content filters like adult, rating, language).
 */
public class FiltersView extends JPanel implements ActionListener, PropertyChangeListener {
    // TODO: Wire apply filters to FiltersController

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Handle apply filters button click
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: React to FiltersViewModel state changes
    }
}
