package use_case.suggestions;

/**
 * TODO: Implements suggestions use case.
 */
public class SuggestionsInteractor implements SuggestionsInputBoundary {
    private final SuggestionsUserDataAccessInterface gateway;
    private final SuggestionsOutputBoundary presenter;

    public SuggestionsInteractor(SuggestionsUserDataAccessInterface gateway,
                                 SuggestionsOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    // TODO: Implement suggestFor
}

