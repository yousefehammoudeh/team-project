package app;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.update_room.UpdateRoomInputBoundary;
import use_case.update_room.UpdateRoomInteractor;
import view.*;

import javax.swing.*;
import java.awt.*;
import java.util.Arrays;

/**
 * Compose application wiring.
 * - Instantiate ViewManagerModel and all ViewModels
 * - Build use case interactors with data-access and presenters
 * - Build controllers and views
 * - Register views to ViewManager and set initial active view
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final RoomDatabase userDataAccessObject = new RoomDatabase();

    private ShortlistView shortlistView;
    private ShortlistViewModel shortlistViewModel;
    private ShortlistPresenter shortlistPresenter;

    private WelcomeView welcomeView;
    private HostDashboardView hostDashboardView;
    private ParticipantsDashboardView participantsDashboardView;
    private SearchView searchView;
    private VoteView voteView;
    private JoinRoomView joinRoomView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        // ViewManager listens to viewManagerModel and switches views
        new ViewManager(cardPanel, cardLayout, viewManagerModel);
    }

    /**
     * Adds the welcome view (entry point).
     * TODO: Replace stub dialogs with actual CreateRoom and JoinRoom
     * views/controllers
     */
    public AppBuilder addWelcomeView() {
        this.welcomeView = new WelcomeView(viewManagerModel);
        cardPanel.add(welcomeView, welcomeView.getViewName());
        return this;
    }

    /**
     * Adds the host dashboard view.
     * TODO: Wire up with CreateRoomViewModel to get actual room data
     * TODO: Add lock shortlist button functionality
     * TODO: Add compute winner button functionality
     */
    public AppBuilder addHostDashboardView() {
        this.hostDashboardView = new HostDashboardView();
        this.hostDashboardView.setViewManagerModel(viewManagerModel);
        this.hostDashboardView.setRoomId("ABC123"); // Stub data
        this.hostDashboardView.updateParticipants(Arrays.asList("Alice", "Bob", "Charlie"));
        cardPanel.add(hostDashboardView, hostDashboardView.getViewName());
        return this;
    }

    /**
     * Adds the participants dashboard view.
     * Participants wait here until host locks the shortlist, then auto-navigate to
     * Vote.
     * TODO: Wire up with JoinRoomViewModel and JoinRoomController
     * TODO: Implement property change listener to auto-navigate to Vote when
     * shortlist locked
     */
    public AppBuilder addParticipantsDashboardView() {
        this.participantsDashboardView = new ParticipantsDashboardView();
        this.participantsDashboardView.setViewManagerModel(viewManagerModel);
        this.participantsDashboardView.setRoomId("ABC123"); // Stub data
        this.participantsDashboardView.updateParticipants(Arrays.asList("Alice", "Bob", "Charlie"));
        cardPanel.add(participantsDashboardView, participantsDashboardView.getViewName());
        return this;
    }

    /**
     * Adds the search view (stub).
     * TODO: Wire up SearchController, SearchInteractor, and TmdbMovieGateway
     * TODO: Add functionality to add movies to shortlist from search results
     */
    public AppBuilder addSearchView() {
        this.searchView = new SearchView();
        this.searchView.setViewManagerModel(viewManagerModel);
        cardPanel.add(searchView, searchView.getViewName());
        return this;
    }

    /**
     * Adds the shortlist view with add/remove movie use cases.
     * FULLY IMPLEMENTED: Add/Remove movie functionality works!
     * TODO: Add lock shortlist functionality
     * TODO: Wire up with SearchView to add movies from search results
     */
    public AppBuilder addShortlistView() {
        this.shortlistViewModel = new ShortlistViewModel();
        this.shortlistView = new ShortlistView(viewManagerModel, shortlistViewModel);
        cardPanel.add(shortlistView, "Shortlist"); // Use exact viewName from ShortlistView
        return this;
    }

    /**
     * Adds the vote view (stub).
     * TODO: Wire up VoteController and VoteInteractor
     * TODO: Load actual shortlisted movies from room data
     * TODO: Implement ballot submission to backend
     * TODO: Add winner calculation and display
     */
    public AppBuilder addVoteView() {
        this.voteView = new VoteView();
        // Stub: show some dummy posters
        this.voteView.setPosterUrls(
                Arrays.asList(
                        "https://image.tmdb.org/t/p/w200/placeholder1.jpg",
                        "https://image.tmdb.org/t/p/w200/placeholder2.jpg"),
                Arrays.asList("movie1", "movie2"));
        this.voteView.setOnSubmit(ranked -> {
            JOptionPane.showMessageDialog(voteView,
                    "Vote submitted (stub)!\nYour ranking: " + ranked);
        });
        cardPanel.add(voteView, voteView.getViewName());
        return this;
    }

    /**
     * Adds the join room view (stub).
     */
    public AppBuilder addJoinRoomView() {
        this.joinRoomView = new JoinRoomView();
        cardPanel.add(joinRoomView, joinRoomView.getViewName());
        return this;
    }

    /**
     * Wires up the add movie use case for shortlist.
     */
    public AppBuilder addAddMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final AddMovieInputBoundary addMovieInputBoundary = new AddMovieInteractor(userDataAccessObject,
                shortlistPresenter);
        final AddMovieController addMovieController = new AddMovieController(addMovieInputBoundary);
        shortlistView.setAddMovieController(addMovieController);
        return this;
    }

    /**
     * Wires up the remove movie use case for shortlist.
     */
    public AppBuilder addRemoveMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final RemoveMovieInputBoundary removeMovieInputBoundary = new RemoveMovieInteractor(userDataAccessObject,
                shortlistPresenter);
        final RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieInputBoundary);
        shortlistView.setRemoveMovieController(removeMovieController);
        return this;
    }

    public AppBuilder addUpdateRoomUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        UpdateRoomInputBoundary updateRoomInputBoundary = new UpdateRoomInteractor(userDataAccessObject,
                shortlistPresenter);
        UpdateRoomController updateRoomController = new UpdateRoomController(updateRoomInputBoundary);
        shortlistView.setUpdateRoomController(updateRoomController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("Movie Night Voting App");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);

        // Set initial view to Welcome
        viewManagerModel.setActiveViewName("Welcome");

        return application;
    }
}
