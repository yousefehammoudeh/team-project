package use_case.winner;

import entity.Movie;

import java.io.IOException;

/**
 * Data Access Interface for fetching movie details in the Winner use case.
 * 
 * This interface follows Clean Architecture principles by:
 * - Living in the use case layer (inner ring)
 * - Defining what the WinnerInteractor needs, not how it's implemented
 * - Allowing concrete implementations (like TmdbMovieGateway) to live in outer
 * rings
 * - Enabling easy testing through stub/mock implementations
 * - Supporting future API swaps without changing the use case
 */
public interface WinnerMovieDataAccessInterface {

    /**
     * Fetches detailed information about a movie by its ID.
     * 
     * @param movieId          the unique identifier of the movie
     * @param appendToResponse optional additional data to fetch (can be null)
     * @return a Movie entity with detailed information
     * @throws IOException if the data fetch operation fails
     */
    Movie fetchDetails(String movieId, String appendToResponse) throws IOException;
}
