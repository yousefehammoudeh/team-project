package view;

import entity.Movie;
import interface_adapter.shortlist.AddMovieController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieResultPanel extends JPanel {

    private AddMovieController addMovieController;

    public MovieResultPanel(ImageIcon poster, Movie movie) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // poster on the left
        JLabel posterLabel = new JLabel(poster);
        add(posterLabel, BorderLayout.WEST);

        // panel for the title & description & add button on the right
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // title
        JLabel titleLabel = new JLabel(movie.getTitle());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 16));
        textPanel.add(titleLabel);

        // description
//        JTextArea descriptionLabel = new JTextArea(description);
//        descriptionLabel.setLineWrap(true);
//        descriptionLabel.setWrapStyleWord(true);
//        descriptionLabel.setEditable(false);
//        descriptionLabel.setOpaque(false);
//        textPanel.add(descriptionLabel);

        // add button
        final JButton addButton = new JButton("Add");
        textPanel.add(addButton);

        // add button action listener
        addButton.addActionListener((e -> {
            if (addMovieController != null) {
                addMovieController.execute(movie.getId());
            } else {
                System.err.println("AddMovieController is not set!");
            }
        }));

        add(textPanel, BorderLayout.CENTER);
    }

    public void setAddMovieController(AddMovieController controller) {
        this.addMovieController = controller;
    }
}

