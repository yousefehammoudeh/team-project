package view;

import interface_adapter.ViewManagerModel;

import javax.swing.*;
import java.awt.*;

/**
 * Welcome screen - entry point for creating or joining a room.
 */
public class WelcomeView extends JPanel {
    private final String viewName = ViewManagerModel.WELCOME_VIEW;
    @SuppressWarnings("unused")
    private final ViewManagerModel viewManagerModel;

    public WelcomeView(ViewManagerModel viewManagerModel) {
        this.viewManagerModel = viewManagerModel;

        setLayout(new BorderLayout(20, 20));
        setBorder(BorderFactory.createEmptyBorder(50, 50, 50, 50));

        // Title
        final JLabel title = new JLabel("Movie Night Voting App", SwingConstants.CENTER);
        title.setFont(new Font("SansSerif", Font.BOLD, 28));
        add(title, BorderLayout.NORTH);

        // Buttons panel
        final JPanel buttonsPanel = new JPanel(new GridLayout(2, 1, 10, 10));

        final JButton createRoomButton = new JButton("Create New Room (Host)");
        createRoomButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        createRoomButton.addActionListener(e -> {
            viewManagerModel.setActiveViewName(ViewManagerModel.CREATE_ROOM_VIEW);
        });

        final JButton joinRoomButton = new JButton("Join Existing Room");
        joinRoomButton.setFont(new Font("SansSerif", Font.PLAIN, 18));
        joinRoomButton.addActionListener(e -> {
            viewManagerModel.setActiveViewName(ViewManagerModel.JOIN_ROOM_VIEW);
        });
        buttonsPanel.add(createRoomButton);
        buttonsPanel.add(joinRoomButton);

        final JPanel centerWrapper = new JPanel(new GridBagLayout());
        centerWrapper.add(buttonsPanel);
        add(centerWrapper, BorderLayout.CENTER);
    }

    public String getViewName() {
        return viewName;
    }
}
