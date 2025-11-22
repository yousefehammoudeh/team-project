package view;

import entity.Movie;
import interface_adapter.search.SearchController;
import interface_adapter.search.SearchViewModel;
import interface_adapter.search.SearchState;
import interface_adapter.shortlist.AddMovieController;

import java.net.MalformedURLException;
import java.util.List;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.net.URL;

/**
 * TODO: Search view (search field, results list, details panel).
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {

    private final String viewName = "Search View";
    private final SearchViewModel searchViewModel;

    private final JLabel roomId;
    private final JButton shortList;

    private final JTextField searchInputField = new JTextField(15);
    private final JLabel searchErrorField = new JLabel();

    private final JButton searchButton;
    private SearchController searchController;

    private final DefaultListModel<String> movieListModel = new DefaultListModel<>();
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
        roomId = new JLabel("room code");
        header.add(roomId);
        shortList = new JButton("shortlist");
        header.add(shortList);

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
            searchController.execute(currentState.getQuery());
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
        this.add(searchListPanel);
    }

    public void setSearchController(SearchController controller) {
        this.searchController = controller;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO: Trigger search via controller
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        SearchState state = (SearchState) evt.getNewValue();

        if (state == null) {
            return; // nothing to update yet
        }

        List<Movie> movies = state.getMovies();
        if (movies == null) {
            return;
        }

        searchListPanel.removeAll(); // searchListPanel = the panel that holds movie result blocks

        for (Movie movie : movies) {
            String p = movie.getPosterPath();
            if (p == null || p.isBlank()) continue;

            String cleaned = p.startsWith("/") ? p : "/" + p;
            String fullUrl = "https://image.tmdb.org/t/p/w200" + cleaned;

            try {
                URL url = new URL(fullUrl);
                ImageIcon icon = new ImageIcon(url);

                JPanel block = new MovieResultPanel(icon, movie.getTitle());
                searchListPanel.add(block);

            } catch (MalformedURLException e) {
                // Print the issue so you know something's wrong
                e.printStackTrace();

                // Optionally: add a placeholder panel without image
                JPanel block = new MovieResultPanel(null, movie.getTitle());
                searchListPanel.add(block);
            }
        }

        searchListPanel.revalidate();
        searchListPanel.repaint();
    }
}
