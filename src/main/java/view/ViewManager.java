package view;

import interface_adapter.ViewManagerModel;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * TODO: Switches between views based on ViewManagerModel active view.
 */
public class ViewManager implements PropertyChangeListener {
    private final ViewManagerModel viewManagerModel;

    public ViewManager(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;
        // TODO: Subscribe to model and handle view switching
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Change active view in UI container
    }
}
