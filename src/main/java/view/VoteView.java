package view;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

/**
 * Simplified VoteView: only displays poster images and a "Submit Vote" button.
 */
public class VoteView extends JPanel {
    private final JPanel postersPanel;
    private final JButton submitButton = new JButton("Submit Vote");

    public VoteView() {
        setLayout(new BorderLayout(8, 8));

        postersPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 8));
        final JScrollPane scroller = new JScrollPane(postersPanel, JScrollPane.VERTICAL_SCROLLBAR_NEVER,
                JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        scroller.setPreferredSize(new Dimension(880, 320));
        add(scroller, BorderLayout.CENTER);

        final JPanel bottom = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        submitButton.addActionListener(e -> {
            // Intentionally minimal: no business logic here. Keep button visible for
            // integration later.
            JOptionPane.showMessageDialog(this, "Submit Vote pressed");
        });
        bottom.add(submitButton);
        add(bottom, BorderLayout.SOUTH);
    }

    /**
     * Replace the posters panel contents with the provided poster image URLs.
     * Images are loaded synchronously (simple, minimal). If loading fails a
     * placeholder label is shown.
     */
    public void setPosterUrls(List<String> posterUrls) {
        postersPanel.removeAll();
        final int posterW = 140;
        final int posterH = 210;

        if (posterUrls == null)
            posterUrls = new ArrayList<>();

        for (String url : posterUrls) {
            JLabel lbl;
            if (url == null || url.isBlank()) {
                lbl = placeholderLabel(posterW, posterH);
            } else {
                try {
                    BufferedImage img = ImageIO.read(URI.create(url).toURL());
                    if (img != null) {
                        Image scaled = img.getScaledInstance(posterW, posterH, Image.SCALE_SMOOTH);
                        lbl = new JLabel(new ImageIcon(scaled));
                    } else {
                        lbl = placeholderLabel(posterW, posterH);
                    }
                } catch (IOException ex) {
                    lbl = placeholderLabel(posterW, posterH);
                }
            }
            lbl.setBorder(BorderFactory.createLineBorder(Color.DARK_GRAY));
            postersPanel.add(lbl);
        }

        revalidate();
        repaint();
    }

    private JLabel placeholderLabel(int w, int h) {
        JLabel l = new JLabel("No Image", SwingConstants.CENTER);
        l.setPreferredSize(new Dimension(w, h));
        l.setMaximumSize(new Dimension(w, h));
        return l;
    }
}
