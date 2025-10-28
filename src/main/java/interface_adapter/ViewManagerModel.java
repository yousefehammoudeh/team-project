package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * TODO: Holds the name of the active view for ViewManager.
 */
public class ViewManagerModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private String activeViewName; // TODO: decide on constants for view names

    public void setActiveViewName(String name) {
        String old = this.activeViewName;
        this.activeViewName = name;
        support.firePropertyChange("activeView", old, name);
    }

    public String getActiveViewName() {
        return activeViewName;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
