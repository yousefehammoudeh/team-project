package interface_adapter.search;

import interface_adapter.ViewModel;

/**
 * TODO: ViewModel wrapper for SearchState.
 */
public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        this.state = new SearchState();
    }

    @Override
    public SearchState getState() {
        return this.state;
    }

    @Override
    public void setState(SearchState state) {
        this.state = state;
        firePropertyChanged();      // notify views
    }
}

