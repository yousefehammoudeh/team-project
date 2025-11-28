package interface_adapter.host_dashboard;

import interface_adapter.ViewModel;
import interface_adapter.ViewManagerModel;

/**
 * ViewModel for the host dashboard.
 */
public class HostDashboardViewModel extends ViewModel<HostDashboardState> {
    public HostDashboardViewModel() {
        super(ViewManagerModel.HOST_DASHBOARD_VIEW);
        setState(new HostDashboardState());
    }
}
