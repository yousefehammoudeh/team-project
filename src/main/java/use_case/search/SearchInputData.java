package use_case.search;

/**
 * Input data for search queries.
 */
public class SearchInputData {
    private final String movieTitle;

    public SearchInputData(String movieTitle) {
        this.movieTitle = movieTitle;
    }

    public String getMovieTitle() {
        return movieTitle;
    }
}
