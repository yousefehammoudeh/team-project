package use_case.search;

import entity.Movie;
import use_case.search.SearchUserDataAccessInterface;
import use_case.search.SearchOutputBoundary;
import view.SearchView;

import javax.swing.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


/**
 * TODO: Implements search & details use case.
 */
public class SearchInteractor implements SearchInputBoundary {
    private final SearchUserDataAccessInterface gateway;
    private final SearchOutputBoundary presenter;

    public SearchInteractor(SearchUserDataAccessInterface gateway, SearchOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    public void execute(SearchInputData searchInputData) {
        final String movieTitle = searchInputData.getMovieTitle();

        try {
            data_access.tmdb.TmdbMovieGateway gw =
                    new data_access.tmdb.TmdbMovieGateway(System.getenv("TMDB_API_KEY"), null, null);

            // list of movies
            List<Movie> movies = gw.search(movieTitle, null);
            if (movies.size() > 5) {
                movies = movies.subList(0, 5);
            }
            // poster stuff
            List<String> posterUrls = new ArrayList<>();
            for (entity.Movie m : movies) {
                String p = m.getPosterPath();
                if (p == null || p.isBlank()) continue;

                String cleaned = p.startsWith("/") ? p : "/" + p;
                posterUrls.add("https://image.tmdb.org/t/p/w200" + cleaned);
            }

            // search output data
            SearchOutputData outputData = new SearchOutputData(movies);

            // presenter
            presenter.present(outputData);

        } catch (Exception e) {
            presenter.presentFailure("Search failed: " + e.getMessage());
        }
    }
}