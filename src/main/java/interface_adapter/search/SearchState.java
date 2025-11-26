package interface_adapter.search;

import entity.Movie;

import java.util.List;

/**
 * TODO: Holds UI state for search results and selected details.
 * Fields to consider:
 * - query
 * - results (list of lightweight movie summaries)
 * - selectedMovieDetails
 * - error
 */
public class SearchState {
    private String query;
    private List<Movie> movies;
    private String error;

    public SearchState() {}

    public String getQuery() { return this.query; }
    public List<Movie> getMovies() { return this.movies; }
    public String getError() { return this.error; }

    public void setQuery(String query) { this.query = query; }
    public void setMovies(List<Movie> movies) { this.movies = movies; }
    public void setError(String error) { this.error = error; }
}
