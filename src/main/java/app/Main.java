package app;

import javax.swing.*;

/**
 * Application entry point.
 * - Initialize UI (Swing) event thread
 * - Delegate to AppBuilder to construct views and use cases
 */
public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            JFrame application = new AppBuilder()
                    .addWelcomeAndDashboards()
                    .addJoinAndCreateFlows()
                    .addShortlistView()
                    .addVoteView()
                    .addWinnerView()
                    .addVoteUseCase()
                    .addSearchUseCase()
                    .addAddMovieUseCase()
                    .addRemoveMovieUseCase()
                    .addUpdateRoomUseCase()
                    .addToggleLockRoomUseCase()
                    .build();
            application.pack();
            application.setLocationRelativeTo(null);
            application.setVisible(true);
        });
    }
}
