package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * Base ViewModel with Observer pattern support.
 */
public class ViewModel<T> {
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
    private final String name;
    protected T state;

    protected ViewModel() {
        this.name = "";
    }

    protected ViewModel(String name) {
        this.name = name;
    }

    public String getViewName() {
        return this.name;
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
