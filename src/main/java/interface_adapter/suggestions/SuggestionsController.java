package interface_adapter.suggestions;

import use_case.suggestions.SuggestionsInputBoundary;

/**
 * TODO: Requests suggestions based on selected/watched movie.
 */
public class SuggestionsController {
    private final SuggestionsInputBoundary interactor;

    public SuggestionsController(SuggestionsInputBoundary interactor) {
        this.interactor = interactor;
    }

    // TODO: Method: suggestFor(movieId)
}

