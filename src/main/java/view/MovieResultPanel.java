package view;

import interface_adapter.shortlist.AddMovieController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class MovieResultPanel extends JPanel {

    private AddMovieController addMovieController;
    private String selectedMovieID;

    public MovieResultPanel(ImageIcon poster, String title) {
        setLayout(new BorderLayout());
        setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // poster on the left
        JLabel posterLabel = new JLabel(poster);
        add(posterLabel, BorderLayout.WEST);

        // panel for the title & description & add button on the right
        JPanel textPanel = new JPanel();
        textPanel.setLayout(new BoxLayout(textPanel, BoxLayout.Y_AXIS));

        // title
        JLabel titleLabel = new JLabel(title);
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
        addButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addMovieController.execute(selectedMovieID);
                selectedMovieID = null;
            }
        });

        add(textPanel, BorderLayout.CENTER);
    }
}

