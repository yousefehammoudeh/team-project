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
        // Subscribe to model and handle view switching
        this.viewManagerModel.addPropertyChangeListener(this);
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // In a completed app this should swap the visible card/panel in the main frame.
        // For now, log the requested active view so composition code can respond.
        if ("activeView".equals(evt.getPropertyName())) {
            System.out.println("ViewManager: switch to view -> " + evt.getNewValue());
        }
    }
}
