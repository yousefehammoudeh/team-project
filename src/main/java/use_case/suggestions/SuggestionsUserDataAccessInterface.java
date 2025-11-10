package use_case.suggestions;

public interface SuggestionsUserDataAccessInterface {
    /**
     * Return a list of movies similar to the given movie id.
     * 
     * @param movieId TMDB movie id
     * @return list of movies (may be empty)
     * @throws java.io.IOException when an IO or parsing error occurs
     */
    java.util.List<entity.Movie> similarTo(String movieId) throws java.io.IOException;
}
