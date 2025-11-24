package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Search view (search field, results list, details panel).
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private ViewManagerModel viewManagerModel;

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Trigger search via controller
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update results list/details panel
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
