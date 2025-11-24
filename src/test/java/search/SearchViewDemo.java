package search;

import javax.swing.*;
import data_access.tmdb.TmdbMovieGateway;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchState;
import interface_adapter.search.SearchViewModel;
import use_case.search.SearchInteractor;
import view.SearchView;

public class SearchViewDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Search View Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Initialize view model with initial state
            SearchViewModel viewModel = new SearchViewModel();
            viewModel.setState(new SearchState());

            // Wire up the Clean Architecture layers
            SearchPresenter presenter = new SearchPresenter(viewModel);

            // Get TMDB API key from environment variable
            String apiKey = System.getenv("TMDB_API_KEY");
            if (apiKey == null || apiKey.isEmpty()) {
                System.err.println("Warning: TMDB_API_KEY not set. Search will fail.");
            }
            TmdbMovieGateway gateway = new TmdbMovieGateway(apiKey, null, null);

            SearchInteractor interactor = new SearchInteractor(gateway, presenter);
            SearchController controller = new SearchController(interactor);

            // Create view and inject controller
            SearchView searchView = new SearchView(viewModel);
            searchView.setSearchController(controller);

            frame.setContentPane(searchView);
            frame.pack();
            frame.setVisible(true);
        });
    }
}