package use_case.search;

import entity.Movie;

import java.util.List;

public interface SearchUserDataAccessInterface {
    /**
     * Search for movies matching the given query and optional filters.
     * 
     * @param query   free-text query
     * @param filters content filters (may be null)
     * @return list of movies (may be empty)
     * @throws java.io.IOException when an IO or parsing error occurs
     */
    List<Movie> search(String query, entity.ContentFilters filters) throws java.io.IOException;

    /**
     * Fetch detailed information for a single movie by id.
     * 
     * @param movieId          TMDB movie id
     * @param appendToResponse optional comma-separated append_to_response values
     *                         (e.g., "videos,images")
     * @return Movie details or null if not found
     * @throws java.io.IOException when an IO or parsing error occurs
     */
    List<Movie> fetchDetails(String movieId, String appendToResponse) throws java.io.IOException;
}
