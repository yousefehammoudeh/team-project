package use_case.filters;

/**
 * TODO: Presenter API for content filters.
 */
public interface FiltersOutputBoundary {
    void present(FiltersOutputData outputData);
    void presentFailure(String message);
}

