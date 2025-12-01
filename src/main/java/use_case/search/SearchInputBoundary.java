package use_case.search;

/**
 * TODO: Interactor API for search & details.
 */
public interface SearchInputBoundary {
    void execute(SearchInputData searchInputData);
    void switchToShortlistView();
    void switchToCreatedRoomView();
}

