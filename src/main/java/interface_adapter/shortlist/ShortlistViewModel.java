package interface_adapter.shortlist;

import interface_adapter.ViewModel;

public class ShortlistViewModel extends ViewModel<ShortlistState> {

    public ShortlistViewModel() {
        super("Shortlist");
        final ShortlistState state = new ShortlistState();
        setState(state);
    }
}
