package interface_adapter.filters;

import use_case.filters.FiltersInputBoundary;

/**
 * TODO: Applies content filters (host only).
 */
public class FiltersController {
    private final FiltersInputBoundary interactor;

    public FiltersController(FiltersInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method: applyFilters(hostToken, filters)
}

