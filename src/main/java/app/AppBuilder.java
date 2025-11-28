package app;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;
import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomPresenter;
import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.join_room.JoinRoomController;
import interface_adapter.join_room.JoinRoomPresenter;
import interface_adapter.join_room.JoinRoomViewModel;
import interface_adapter.joined_room.JoinedRoomViewModel;
import interface_adapter.host_dashboard.HostDashboardViewModel;
import use_case.create_room.CreateRoomInputBoundary;
import use_case.create_room.CreateRoomInteractor;
import use_case.join_room.JoinRoomInputBoundary;
import use_case.join_room.JoinRoomInteractor;
import view.CreateRoomView;
import view.JoinRoomView;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.toggle_lock_room.ToggleLockRoomInputBoundary;
import use_case.toggle_lock_room.ToggleLockRoomInteractor;
import use_case.update_room.UpdateRoomInputBoundary;
import use_case.update_room.UpdateRoomInteractor;
import view.ShortlistView;
import view.WelcomeView;
import view.HostDashboardView;
import view.ParticipantsDashboardView;
import view.ViewManager;
import interface_adapter.search.*;
import use_case.search.*;
import data_access.tmdb.TmdbMovieGateway;
import javax.swing.*;
import java.awt.*;

/**
 * Composes application wiring: view models, interactors, presenters,
 * controllers,
 * and Swing views registered in a CardLayout with an initial Welcome view.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final RoomDatabase userDataAccessObject = new RoomDatabase();

    private ShortlistView shortlistView;
    private ShortlistViewModel shortlistViewModel;
    private ShortlistPresenter shortlistPresenter;
    private HostDashboardViewModel hostDashboardViewModel;
    private interface_adapter.shortlist.AddMovieController addMovieController;
    private view.SearchView searchView;

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        new ViewManager(cardPanel, cardLayout, viewManagerModel);
    }

    public AppBuilder addWelcomeAndDashboards() {
        // Welcome view
        final WelcomeView welcomeView = new WelcomeView(viewManagerModel);
        cardPanel.add(welcomeView, ViewManagerModel.WELCOME_VIEW);

        // Host dashboard
        this.hostDashboardViewModel = new HostDashboardViewModel();
        final HostDashboardView hostDashboardView = new HostDashboardView(hostDashboardViewModel);
        hostDashboardView.setViewManagerModel(viewManagerModel);
        cardPanel.add(hostDashboardView, ViewManagerModel.HOST_DASHBOARD_VIEW);

        // Participants dashboard (registered under joined-room view name for presenter
        // routing)
        final JoinedRoomViewModel joinedRoomViewModel = new JoinedRoomViewModel();
        final ParticipantsDashboardView participantsDashboardView = new ParticipantsDashboardView(joinedRoomViewModel);
        cardPanel.add(participantsDashboardView, joinedRoomViewModel.getViewName());

        return this;
    }

    public AppBuilder addJoinAndCreateFlows() {
        // Create Room flow
        final CreateRoomViewModel createRoomViewModel = new CreateRoomViewModel();
        final CreateRoomPresenter createRoomPresenter = new CreateRoomPresenter(createRoomViewModel,
                hostDashboardViewModel, viewManagerModel);
        final CreateRoomInputBoundary createRoomInteractor = new CreateRoomInteractor(userDataAccessObject,
                createRoomPresenter);
        final CreateRoomController createRoomController = new CreateRoomController(createRoomInteractor);
        final CreateRoomView createRoomView = new CreateRoomView(createRoomViewModel);
        createRoomView.setController(createRoomController);
        cardPanel.add(createRoomView, createRoomView.getViewName());

        // Join Room flow
        final JoinRoomViewModel joinRoomViewModel = new JoinRoomViewModel();
        final JoinedRoomViewModel joinedRoomViewModel = new JoinedRoomViewModel();
        final JoinRoomPresenter joinRoomPresenter = new JoinRoomPresenter(joinRoomViewModel, joinedRoomViewModel,
                createRoomViewModel, viewManagerModel);
        final JoinRoomInputBoundary joinRoomInteractor = new JoinRoomInteractor(userDataAccessObject,
                joinRoomPresenter);
        final JoinRoomController joinRoomController = new JoinRoomController(joinRoomInteractor);
        final JoinRoomView joinRoomView = new JoinRoomView(joinRoomViewModel);
        joinRoomView.setJoinRoomController(joinRoomController);
        cardPanel.add(joinRoomView, joinRoomView.getViewName());

        return this;
    }

    public AppBuilder addShortlistView() {
        this.shortlistViewModel = new ShortlistViewModel();
        this.shortlistView = new ShortlistView(viewManagerModel, shortlistViewModel);
        cardPanel.add(shortlistView, ViewManagerModel.SHORTLIST_VIEW);
        return this;
    }

    public AppBuilder addAddMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final AddMovieInputBoundary addMovieInputBoundary = new AddMovieInteractor(userDataAccessObject,
            shortlistPresenter);
        this.addMovieController = new AddMovieController(addMovieInputBoundary);
        shortlistView.setAddMovieController(this.addMovieController);
        // If search view already exists, inject the same controller so "Add" from search works
        if (this.searchView != null) {
            this.searchView.setAddMovieController(this.addMovieController);
        }
        return this;
    }

    public AppBuilder addRemoveMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final RemoveMovieInputBoundary removeMovieInputBoundary =
                new RemoveMovieInteractor(userDataAccessObject, shortlistPresenter);
        final RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieInputBoundary);
        shortlistView.setRemoveMovieController(removeMovieController);
        return this;
    }

    public AppBuilder addUpdateRoomUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        UpdateRoomInputBoundary updateRoomInputBoundary =
            new UpdateRoomInteractor(userDataAccessObject, shortlistPresenter);
        UpdateRoomController updateRoomController = new UpdateRoomController(updateRoomInputBoundary);
        shortlistView.setUpdateRoomController(updateRoomController);
        return this;
    }

    public AppBuilder addSearchUseCase() {
        // Create Search ViewModel and Presenter
        final SearchViewModel searchViewModel = new SearchViewModel();
        final SearchPresenter searchPresenter = new SearchPresenter(searchViewModel, viewManagerModel);

        // Gateway for TMDB-backed search
        final SearchUserDataAccessInterface gateway = new TmdbMovieGateway();

        // Interactor and Controller
        final SearchInputBoundary searchInteractor = new SearchInteractor(gateway, searchPresenter);
        final SearchController searchController = new SearchController(searchInteractor);

        // View
        this.searchView = new view.SearchView(searchViewModel);
        this.searchView.setSearchController(searchController);
        // Inject AddMovieController if available so "Add" buttons work from search
        // results
        if (this.addMovieController != null) {
            this.searchView.setAddMovieController(this.addMovieController);
        }
        cardPanel.add(this.searchView, ViewManagerModel.SEARCH_VIEW);
        return this;
    }

    public AppBuilder addToggleLockRoomUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        ToggleLockRoomInputBoundary toggleLockRoomInputBoundary =
                new ToggleLockRoomInteractor(userDataAccessObject, shortlistPresenter);
        ToggleLockRoomController toggleLockRoomController = new ToggleLockRoomController(toggleLockRoomInputBoundary);
        shortlistView.setToggleLockRoomController(toggleLockRoomController);
        return this;
    }

    public JFrame build() {
        final JFrame application = new JFrame("ReelRound");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);
        // Set initial view to welcome
        viewManagerModel.setActiveViewName(ViewManagerModel.WELCOME_VIEW);
        return application;
    }
}
