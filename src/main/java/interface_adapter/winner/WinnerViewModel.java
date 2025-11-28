package interface_adapter.winner;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

public class WinnerViewModel {
    private final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final WinnerState state = new WinnerState();

    public WinnerState getState() {
        return state;
    }

    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, state);
    }

    public String getViewName() {
        return "Winner";
    }
}
