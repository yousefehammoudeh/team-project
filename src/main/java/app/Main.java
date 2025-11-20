package app;

import javax.swing.*;

/**
 * TODO: Application entry point.
 * - Initialize UI (Swing) event thread if applicable
 * - Delegate to AppBuilder to construct views and use cases
 */
public class Main {
    public static void main(String[] args) {
        JFrame application = new AppBuilder()
                .addShortlistView()
                .addAddMovieUseCase()
                .addRemoveMovieUseCase()
                .build();
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
