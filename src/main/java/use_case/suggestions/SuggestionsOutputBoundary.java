package use_case.suggestions;

/**
 * TODO: Presenter API for suggestions.
 */
public interface SuggestionsOutputBoundary {
    void present(SuggestionsOutputData outputData);
    void presentFailure(String message);
}

