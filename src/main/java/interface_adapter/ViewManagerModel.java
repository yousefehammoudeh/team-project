package interface_adapter;

import java.beans.PropertyChangeListener;

/**
 * Holds the name of the active view for ViewManager and provides
 * constants for all view names used across the app.
 */
public class ViewManagerModel extends ViewModel<String> {
    // Common view name constants
    public static final String WELCOME_VIEW = "Welcome";
    public static final String HOST_DASHBOARD_VIEW = "HostDashboard";
    public static final String PARTICIPANTS_DASHBOARD_VIEW = "ParticipantsDashboard";
    public static final String SHORTLIST_VIEW = "Shortlist";
    public static final String VOTE_VIEW = "Vote";
    public static final String SEARCH_VIEW = "Search";

    private String activeViewName;

    public void setActiveViewName(String name) {
        String old = this.activeViewName;
        this.activeViewName = name;
        support.firePropertyChange("activeView", old, name);
    }

    public String getActiveViewName() {
        return activeViewName;
    }

    @Override
    public void addPropertyChangeListener(PropertyChangeListener l) {
        support.addPropertyChangeListener(l);
    }

    @Override
    public void removePropertyChangeListener(PropertyChangeListener l) {
        support.removePropertyChangeListener(l);
    }
}
