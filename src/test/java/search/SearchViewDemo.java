package search;

import javax.swing.*;
import interface_adapter.search.SearchViewModel;
import view.SearchView;

public class SearchViewDemo {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame frame = new JFrame("Search View Demo");
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            // TODO: pass a real SearchViewModel — or mock it
            SearchViewModel vm = new SearchViewModel();
            SearchView searchView = new SearchView(vm);

            frame.setContentPane(searchView);
            frame.pack();
            frame.setVisible(true);
        });
    }
}