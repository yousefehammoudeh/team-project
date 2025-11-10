package data_access.tmdb;

import entity.ContentFilters;
import entity.Movie;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TmdbMovieGatewayTest {

    @Test
    void testSearchReturnsResults() throws Exception {
        String apiKey = System.getenv("TMDB_API_KEY");
        assertNotNull(apiKey, "TMDB_API_KEY not set. Export your TMDB v3 API key to run this test.");

        TmdbMovieGateway gw = new TmdbMovieGateway(apiKey, null, null);
        List<Movie> results = gw.search("lion", ContentFilters.defaults());
        assertNotNull(results);
        assertFalse(results.isEmpty(), "Expected at least one search result for 'lion'");
    }

    @Test
    void testFetchDetailsReturnsMovie() throws Exception {
        String apiKey = System.getenv("TMDB_API_KEY");
        assertNotNull(apiKey, "TMDB_API_KEY not set. Export your TMDB v3 API key to run this test.");

        TmdbMovieGateway gw = new TmdbMovieGateway(apiKey, null, null);
        Movie m = gw.fetchDetails("343611", null);
        assertNotNull(m);
        assertEquals("343611", m.getId());
    }
}
