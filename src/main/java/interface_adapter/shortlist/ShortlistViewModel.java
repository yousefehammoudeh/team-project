package interface_adapter.shortlist;

import interface_adapter.ViewModel;

/**
 * TODO: ViewModel wrapper for ShortlistState.
 */
public class ShortlistViewModel extends ViewModel<ShortlistState> {
    public ShortlistViewModel() {
        final ShortlistState state = new ShortlistState();
        setState(state);
    }
}

