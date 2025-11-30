package view;

import interface_adapter.winner.WinnerController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ComponentAdapter;
import java.awt.event.ComponentEvent;

/**
 * Winner view: shows the winning movie poster and details.
 * Currently a simple placeholder; wiring can set content via setters.
 */
public class WinnerView extends JPanel {
    private final JLabel titleLabel = new JLabel("Winner: ", SwingConstants.CENTER);
    private final JLabel posterLabel = new JLabel();
    private final JTextArea detailsArea = new JTextArea();
    private WinnerController winnerController;

    public WinnerView() {
        setLayout(new BorderLayout(10, 10));
        titleLabel.setFont(new Font("Serif", Font.BOLD, 20));
        add(titleLabel, BorderLayout.NORTH);

        posterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        add(posterLabel, BorderLayout.CENTER);

        detailsArea.setEditable(false);
        detailsArea.setLineWrap(true);
        detailsArea.setWrapStyleWord(true);
        add(new JScrollPane(detailsArea), BorderLayout.SOUTH);

        // Load winner data when view becomes visible
        addComponentListener(new ComponentAdapter() {
            @Override
            public void componentShown(ComponentEvent e) {
                if (winnerController != null) {
                    winnerController.displayWinner();
                }
            }
        });
    }

    public String getViewName() {
        return "Winner";
    }

    public void setWinnerTitle(String title) {
        titleLabel.setText("Winner: " + (title == null ? "" : title));
    }

    public void setPoster(ImageIcon icon) {
        posterLabel.setIcon(icon);
    }

    public void setDetails(String details) {
        detailsArea.setText(details == null ? "" : details);
    }

    public void setWinnerController(WinnerController controller) {
        this.winnerController = controller;
    }
}
