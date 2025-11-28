package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Simple generic ViewModel with property change support and a typed state.
 */
public class ViewModel<T> {
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
    @SuppressWarnings("unused")
    private final String name;
    protected T state;

    protected ViewModel() {
        this.name = "";
    }

    protected ViewModel(String name) {
        this.name = name;
    }

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        support.removePropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        support.firePropertyChange("state", null, this.state);
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }
}
