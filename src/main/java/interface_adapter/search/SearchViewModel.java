package interface_adapter.search;

import interface_adapter.ViewModel;

public class SearchViewModel extends ViewModel<SearchState> {

    public SearchViewModel() {
        super("Search");
        this.state = new SearchState();
    }

    @Override
    public SearchState getState() {
        return this.state;
    }

    @Override
    public void setState(SearchState state) {
        this.state = state;
        firePropertyChanged(); // notify views
    }

    // Uses base getViewName()
}
