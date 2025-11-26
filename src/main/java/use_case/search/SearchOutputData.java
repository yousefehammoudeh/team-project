package use_case.search;

import entity.Movie;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: Output for search results and/or details payload.
 */
public class SearchOutputData {
    private final List<Movie> movies;
    private final List<String> posterUrls;

    public SearchOutputData(List<Movie> movies, List<String> posterUrls) {
        this.movies = movies;
        this.posterUrls = posterUrls;
    }

    public List<Movie> getMovies() {
        return movies;
    }

    public List<String> getPosterUrls() {
        return posterUrls;
    }
}
