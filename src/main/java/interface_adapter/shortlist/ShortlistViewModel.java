package interface_adapter.shortlist;

import interface_adapter.ViewManagerModel;
import interface_adapter.ViewModel;

public class ShortlistViewModel extends ViewModel<ShortlistState> {

    public ShortlistViewModel() {
        super(ViewManagerModel.SHORTLIST_VIEW);
        final ShortlistState state = new ShortlistState();
        setState(state);
    }
}
