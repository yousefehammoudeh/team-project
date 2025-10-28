package interface_adapter;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;

/**
 * TODO: Mirror CA-lab ViewModel helper for property change support.
 * - Hold state T
 * - Delegate add/remove listener and firePropertyChange
 */
public class ViewModel<T> {
    // TODO: replicate structure from ca-lab with TODO stubs
    protected final PropertyChangeSupport support = new PropertyChangeSupport(this);
    protected T state;

    public void addPropertyChangeListener(PropertyChangeListener listener) {
        // TODO: delegate to support
        support.addPropertyChangeListener(listener);
    }

    public void removePropertyChangeListener(PropertyChangeListener listener) {
        // TODO: delegate to support
        support.removePropertyChangeListener(listener);
    }

    public void firePropertyChanged() {
        // TODO: fire a generic change (no specific property)
        support.firePropertyChange("state", null, this.state);
    }

    public T getState() {
        return state;
    }

    public void setState(T state) {
        this.state = state;
    }
}
