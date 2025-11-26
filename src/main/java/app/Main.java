package app;

import javax.swing.*;

/**
 * Application entry point.
 * - Initialize UI (Swing) event thread
 * - Delegate to AppBuilder to construct views and use cases
 */
public class Main {
    public static void main(String[] args) {
        // Run on EDT (Event Dispatch Thread)
        SwingUtilities.invokeLater(() -> {
            JFrame application = new AppBuilder()
                    // Add all views
                    .addWelcomeView()
                    .addHostDashboardView()
                    .addParticipantsDashboardView()
                    .addSearchView()
                    .addShortlistView()
                    .addVoteView()
                    .addJoinRoomView()
                    // Wire up use cases for shortlist
                    .addAddMovieUseCase()
                    .addRemoveMovieUseCase()
                    .addUpdateRoomUseCase()
                    // Build the application
                    .build();

            application.setSize(900, 650);
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
