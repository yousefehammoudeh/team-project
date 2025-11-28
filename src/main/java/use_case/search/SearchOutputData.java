package use_case.search;

import entity.Movie;

import java.util.List;

/**
 * TODO: Output for search results and/or details payload.
 */
public class SearchOutputData {
    private final List<Movie> movies;

    public SearchOutputData(List<Movie> movies) {
        this.movies = movies;
    }

    public List<Movie> getMovies() {
        return movies;
    }
}
