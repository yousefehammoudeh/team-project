package search;

import view.SearchView;
import view.ShortlistView;

import interface_adapter.ViewManagerModel;

// Search imports (existing in your project)
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchPresenter;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchState;
import use_case.search.SearchInteractor;
import data_access.tmdb.TmdbMovieGateway;

// Shortlist imports (existing in your project)
import interface_adapter.shortlist.AddMovieController;
import interface_adapter.shortlist.RemoveMovieController;
import interface_adapter.shortlist.UpdateRoomController;
import interface_adapter.shortlist.ShortlistViewModel;
import interface_adapter.shortlist.ShortlistPresenter;

import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInputData;

import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInputData;

import use_case.update_room.UpdateRoomInputBoundary;

import use_case.shortlist.ShortlistOutputData;

import okhttp3.OkHttpClient;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Demo launcher that wires SearchView and ShortlistView so you can test both.
 *
 * - Uses your TMDB gateway for Search (requires TMDB_API_KEY env var).
 * - Uses small in-memory implementations of Add/Remove/Update input boundaries
 * so Shortlist works.
 */
public class DemoLauncher {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            // FRAME
            JFrame frame = new JFrame("Search + Shortlist Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // Shared view manager (used by your ShortlistView)
            ViewManagerModel viewManagerModel = new ViewManagerModel();
            viewManagerModel.setActiveViewName("Search");

            // -----------------------
            // SEARCH SETUP
            // -----------------------
            SearchViewModel searchViewModel = new SearchViewModel();
            searchViewModel.setState(new SearchState());

            // Presenter that updates the SearchViewModel (assumes your constructor accepts
            // these params)
            SearchPresenter searchPresenter = new SearchPresenter(searchViewModel, viewManagerModel);

            // TMDB gateway; requires env var TMDB_API_KEY
            String apiKey = System.getenv("TMDB_API_KEY");
            if (apiKey == null || apiKey.isBlank()) {
                System.err.println("WARNING: TMDB_API_KEY not set; search will fail.");
            }
            TmdbMovieGateway tmdbGateway = new TmdbMovieGateway(apiKey, null, new OkHttpClient());

            // Interactor + controller for search (your existing classes)
            SearchInteractor searchInteractor = new SearchInteractor(tmdbGateway, searchPresenter);
            SearchController searchController = new SearchController(searchInteractor);

            // -----------------------
            // SHORTLIST SETUP
            // -----------------------
            ShortlistViewModel shortlistViewModel = new ShortlistViewModel();
            ShortlistPresenter shortlistPresenter = new ShortlistPresenter(shortlistViewModel);

            // In-memory data store backing the demo shortlist:
            final List<String> shortlistStore = new ArrayList<>();
            final boolean[] lockedFlag = new boolean[] { false }; // mutable holder

            // Implement ShortlistOutputBoundary using your presenter (we already have
            // shortlistPresenter),
            // but the input-boundaries below will call shortlistPresenter directly.

            // --- AddMovieInputBoundary (in-memory) ---
            AddMovieInputBoundary addMovieBoundary = new AddMovieInputBoundary() {
                @Override
                public void execute(AddMovieInputData addMovieInputData) {
                    String id = addMovieInputData.getMovieID();
                    // mimic AddMovieInteractor behaviour: check locked, duplicate, return output
                    // presenter
                    if (lockedFlag[0]) {
                        shortlistPresenter.presentFailure("The shortlist is locked.");
                        return;
                    }
                    if (shortlistStore.contains(id)) {
                        shortlistPresenter.presentFailure("The movie already exists.");
                        return;
                    }
                    shortlistStore.add(id);
                    ShortlistOutputData out = new ShortlistOutputData(new ArrayList<>(shortlistStore), lockedFlag[0]);
                    shortlistPresenter.present(out);
                }
            };

            // --- RemoveMovieInputBoundary (in-memory) ---
            RemoveMovieInputBoundary removeMovieBoundary = new RemoveMovieInputBoundary() {
                @Override
                public void execute(RemoveMovieInputData removeMovieInputData) {
                    String id = removeMovieInputData.getMovieID();
                    if (lockedFlag[0]) {
                        shortlistPresenter.presentFailure("The room is locked.");
                        return;
                    }
                    if (!shortlistStore.remove(id)) {
                        shortlistPresenter.presentFailure("The movie is not in the shortlist.");
                        return;
                    }
                    ShortlistOutputData out = new ShortlistOutputData(new ArrayList<>(shortlistStore), lockedFlag[0]);
                    shortlistPresenter.present(out);
                }
            };

            // --- UpdateRoomInputBoundary (in-memory) ---
            // For demo this just returns the current shortlist via presenter.
            UpdateRoomInputBoundary updateRoomBoundary = new UpdateRoomInputBoundary() {
                @Override
                public void execute() {
                    // In real interactor, there might be rate-limits and DataAccessException
                    // handling.
                    ShortlistOutputData out = new ShortlistOutputData(new ArrayList<>(shortlistStore), lockedFlag[0]);
                    shortlistPresenter.present(out);
                }
            };

            // Controllers (your adapter controllers)
            AddMovieController addMovieController = new AddMovieController(addMovieBoundary);
            RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieBoundary);
            UpdateRoomController updateRoomController = new UpdateRoomController(updateRoomBoundary);

            // Search view
            SearchView searchView = new SearchView(searchViewModel);
            searchView.setSearchController(searchController);
            searchView.setViewManagerModel(viewManagerModel);
            searchView.setAddMovieController(addMovieController);

            // Shortlist view: pass viewManagerModel and shortlistViewModel (your
            // constructor)
            ShortlistView shortlistView = new ShortlistView(viewManagerModel, shortlistViewModel);
            shortlistView.setAddMovieController(addMovieController);
            shortlistView.setRemoveMovieController(removeMovieController);
            shortlistView.setUpdateRoomController(updateRoomController);
            shortlistView.setViewManagerModel(viewManagerModel);

            // -----------------------
            // UI: CardLayout (Search <-> Shortlist)
            // -----------------------
            JPanel cards = new JPanel(new CardLayout());
            cards.add(searchView, "Search");
            cards.add(shortlistView, "Shortlist");

            // Add a small toolbar with Back button (to return from Shortlist to Search)
            JPanel toolbar = new JPanel(new FlowLayout(FlowLayout.LEFT));
            JButton backButton = new JButton("Back to Search");
            backButton.addActionListener(e -> {
                viewManagerModel.setActiveViewName("Search");
            });
            toolbar.add(backButton);

            // Also add a button to go to shortlist (for convenience) — the SearchView
            // already has a shortlist button,
            // but adding this here ensures the demo always has navigation available.
            JButton toShortlistBtn = new JButton("Open Shortlist");
            toShortlistBtn.addActionListener(e -> viewManagerModel.setActiveViewName("Shortlist"));
            toolbar.add(toShortlistBtn);

            // Listen for ViewManagerModel changes and switch card layout
            viewManagerModel.addPropertyChangeListener(evt -> {
                Object newVal = evt.getNewValue();
                if (newVal instanceof String) {
                    String newView = (String) newVal;
                    CardLayout cl = (CardLayout) cards.getLayout();
                    cl.show(cards, newView);
                }
            });

            // Put toolbar + cards in the frame
            JPanel root = new JPanel(new BorderLayout());
            root.add(toolbar, BorderLayout.NORTH);
            root.add(cards, BorderLayout.CENTER);

            frame.setContentPane(root);
            frame.pack();
            frame.setSize(800, 600);
            frame.setLocationRelativeTo(null);
            frame.setVisible(true);
        });
    }
}
