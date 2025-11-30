package view;

import interface_adapter.ViewManagerModel;

import entity.Movie;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.shortlist.AddMovieController;

import java.util.List;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Search view: query input, results list and add-to-shortlist actions.
 */
public class SearchView extends JPanel implements PropertyChangeListener {

    @SuppressWarnings("unused")
    private final String viewName = "Search View";
    private final SearchViewModel searchViewModel;
    @SuppressWarnings("unused")
    private ViewManagerModel viewManagerModel;

    private final JButton dashboard;
    private final JLabel roomId;
    private final JButton shortList;

    private final JTextField searchInputField = new JTextField(15);
    @SuppressWarnings("unused")
    private final JLabel searchErrorField = new JLabel();

    private final JButton searchButton;
    private SearchController searchController;

    @SuppressWarnings("unused")
    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
    @SuppressWarnings("unused")
    private String selectedMovieID;
    private JPanel searchListPanel;

    private AddMovieController addMovieController;

    public SearchView(SearchViewModel searchViewModel) {

        this.searchViewModel = searchViewModel;
        this.searchViewModel.addPropertyChangeListener(this);
        setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));

        // window title
        // final JLabel title = new JLabel("Search Screen");
        // title.setAlignmentX(Component.CENTER_ALIGNMENT);

        // header; room id, button to the shortlist
        final JPanel header = new JPanel();
        roomId = new JLabel("Room ID: Not Set");
        roomId.setFont(new Font("Serif", Font.BOLD, 16));
        header.add(roomId);
        dashboard = new JButton("Dashboard");
        header.add(dashboard);
        shortList = new JButton("Shortlist");
        header.add(shortList);

        dashboard.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        searchController.switchToHostDashboardView();
                    }
                });

        shortList.addActionListener(
                new ActionListener() {
                    public void actionPerformed(ActionEvent evt) {
                        searchController.switchToShortlistView();
                    }
                });

        // the search bar
        final JPanel searchComponents = new JPanel();
        final LabelTextPanel searchBar = new LabelTextPanel(
                new JLabel("Search"), searchInputField);
        searchComponents.add(searchBar);
        searchButton = new JButton("search");
        searchComponents.add(searchButton);

        // search button action listener
        searchButton.addActionListener(e -> {
            // Make sure the ViewModel has a state
            SearchState currentState = searchViewModel.getState();
            if (currentState == null) {
                currentState = new SearchState();
                searchViewModel.setState(currentState);
            }

            // Update the query from the text field
            currentState.setQuery(searchInputField.getText());

            // Fire change so other listeners (if any) update
            searchViewModel.firePropertyChanged();

            // Execute the search
            this.searchController.execute(currentState.getQuery());
        });

        // the movie list display
        searchListPanel = new JPanel();

        searchListPanel.setLayout(new BoxLayout(searchListPanel, BoxLayout.Y_AXIS));
        final JScrollPane scrollPane = new JScrollPane(searchListPanel);
        scrollPane.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);

        // this.add(title);
        this.add(header);
        this.add(searchComponents);
        this.add(scrollPane);
    }

    public void setSearchController(SearchController controller) {
        this.searchController = controller;
    }

    public void setAddMovieController(AddMovieController controller) {
        this.addMovieController = controller;
    }

    private static final ImageIcon PLACEHOLDER_ICON = new ImageIcon(
            SearchView.class.getClassLoader().getResource("placeholder.png"),
            "placeholder.png not found in resources");

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState state = (SearchState) evt.getNewValue();

        if (state == null)
            return;

        // Update room ID if present
        if (state.getRoomId() != null) {
            roomId.setText("Room ID: " + state.getRoomId());
        }

        List<Movie> movies = state.getMovies();
        if (movies == null)
            return;

        searchListPanel.removeAll();

        for (Movie movie : movies) {
            ImageIcon icon = PLACEHOLDER_ICON;
            String posterPath = movie.getPosterPath();

            if (posterPath != null && !posterPath.isBlank()) {
                try {
                    String cleaned = posterPath.startsWith("/") ? posterPath : "/" + posterPath;
                    java.net.URI uri = java.net.URI.create("https://image.tmdb.org/t/p/w200" + cleaned);
                    icon = new ImageIcon(uri.toURL());
                } catch (Exception ignored) {
                }
            }

            MovieResultPanel block = new MovieResultPanel(icon, movie);
            block.setAddMovieController(addMovieController);

            JPanel wrapper = new JPanel(new BorderLayout());
            wrapper.add(block, BorderLayout.NORTH);

            searchListPanel.add(wrapper);
        }

        searchListPanel.revalidate();
        searchListPanel.repaint();
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }
}
