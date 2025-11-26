package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

/**
 * Search view - search TMDB for movies and add them to shortlist.
 * STUB: Basic UI present, but not wired up
 * TODO: Wire up SearchController to handle search queries
 * TODO: Display search results in scrollable list with posters
 * TODO: Add "Add to Shortlist" button for each movie result
 * TODO: Show movie details when a result is clicked
 * TODO: Integrate with TmdbMovieGateway for actual API calls
 */
public class SearchView extends JPanel implements ActionListener, PropertyChangeListener {
    private final String viewName = "Search";
    private ViewManagerModel viewManagerModel;
    private JTextField searchField;
    private JButton searchButton;
    private JButton backButton;

    public SearchView() {
        setLayout(new BorderLayout(10, 10));

        // Title
        final JLabel title = new JLabel("Search Movies (Stub)", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 20));
        add(title, BorderLayout.NORTH);

        // Search panel
        final JPanel searchPanel = new JPanel();
        searchField = new JTextField(20);
        searchButton = new JButton("Search");
        searchButton.addActionListener(this);
        searchPanel.add(new JLabel("Query:"));
        searchPanel.add(searchField);
        searchPanel.add(searchButton);
        add(searchPanel, BorderLayout.CENTER);

        // Back button
        backButton = new JButton("Back to Dashboard");
        backButton.addActionListener(this);
        final JPanel bottomPanel = new JPanel();
        bottomPanel.add(backButton);
        add(bottomPanel, BorderLayout.SOUTH);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == searchButton) {
            JOptionPane.showMessageDialog(this,
                    "Search functionality coming soon!\nQuery: " + searchField.getText());
        } else if (e.getSource() == backButton && viewManagerModel != null) {
            viewManagerModel.setActiveViewName("HostDashboard");
        }
    }

    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        // TODO: Update results list/details panel
    }

    public void setViewManagerModel(ViewManagerModel vm) {
        this.viewManagerModel = vm;
    }

    public String getViewName() {
        return viewName;
    }
}
