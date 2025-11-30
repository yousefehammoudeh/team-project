package data_access.tmdb;

import entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assumptions.assumeTrue;

class TmdbMovieGatewayTest {

    @Test
    void testSearchReturnsResults() throws Exception {
        String apiKey = System.getenv("TMDB_API_KEY");
        assumeTrue(apiKey != null && !apiKey.isBlank(), "Skipping TMDB search test: API key not set");

        TmdbMovieGateway gw = new TmdbMovieGateway(apiKey, null, null);
        List<Movie> results = gw.search("lion");
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Expected at least one search result for 'lion'");
    }

    @Test
    void testFetchDetailsReturnsMovie() throws Exception {
        String apiKey = System.getenv("TMDB_API_KEY");
        assumeTrue(apiKey != null && !apiKey.isBlank(), "Skipping TMDB details test: API key not set");

        TmdbMovieGateway gw = new TmdbMovieGateway(apiKey, null, null);
        Movie m = gw.fetchDetails("343611", null);
        assertNotNull(m);
        assertEquals("343611", m.getId());
    }
}
