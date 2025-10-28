package use_case.filters;

/**
 * TODO: Implements content filters application.
 */
public class FiltersInteractor implements FiltersInputBoundary {
    private final FiltersUserDataAccessInterface gateway;
    private final FiltersOutputBoundary presenter;

    public FiltersInteractor(FiltersUserDataAccessInterface gateway,
                             FiltersOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    // TODO: Implement apply(filters, hostToken)
}

