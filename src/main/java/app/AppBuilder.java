package app;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.created_room.LeaveRoomController;
import interface_adapter.created_room.LeaveRoomPresenter;
import interface_adapter.shortlist.*;
import interface_adapter.create_room.CreateRoomController;
import interface_adapter.create_room.CreateRoomPresenter;
import interface_adapter.create_room.CreateRoomViewModel;
import interface_adapter.join_room.JoinRoomController;
import interface_adapter.join_room.JoinRoomPresenter;
import interface_adapter.join_room.JoinRoomViewModel;
import use_case.create_room.CreateRoomInputBoundary;
import use_case.create_room.CreateRoomInteractor;
import use_case.join_room.JoinRoomInputBoundary;
import use_case.join_room.JoinRoomInteractor;
import use_case.leave_room.LeaveRoomInputBoundary;
import use_case.leave_room.LeaveRoomInteractor;
import use_case.leave_room.LeaveRoomOutputBoundary;
import view.*;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.toggle_lock_room.ToggleLockRoomInputBoundary;
import use_case.toggle_lock_room.ToggleLockRoomInteractor;
import use_case.update_room.UpdateRoomInputBoundary;
import use_case.update_room.UpdateRoomInteractor;
import interface_adapter.search.*;
import use_case.search.*;
import data_access.tmdb.TmdbMovieGateway;
import javax.swing.*;
import java.awt.*;

/**
 * Assembles the application using Builder pattern.
 * Integrates Factory, Façade, and Observer patterns throughout.
 */
public class AppBuilder {
    private final JPanel cardPanel = new JPanel();
    private final CardLayout cardLayout = new CardLayout();
    private final ViewManagerModel viewManagerModel = new ViewManagerModel();

    private final RoomDatabase userDataAccessObject = new RoomDatabase();

    private ShortlistView shortlistView;
    private ShortlistViewModel shortlistViewModel;
    private ShortlistPresenter shortlistPresenter;
    private CreatedRoomView createdRoomView;
    private CreatedRoomViewModel createdRoomViewModel;
    private interface_adapter.shortlist.AddMovieController addMovieController;
    private view.SearchView searchView;
    private SearchViewModel searchViewModel;
    private interface_adapter.vote.VoteViewModel voteViewModel;
    private view.VoteView voteView;
    private interface_adapter.winner.WinnerViewModel winnerViewModel;
    private view.WinnerView winnerView;
    private interface_adapter.shortlist.UpdateRoomController updateRoomController; // store for winner refresh

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        new ViewManager(cardPanel, cardLayout, viewManagerModel);
    }

    public AppBuilder addWelcomeAndDashboards() {
        // Welcome view
        final WelcomeView welcomeView = new WelcomeView(viewManagerModel);
        cardPanel.add(welcomeView, ViewManagerModel.WELCOME_VIEW);

        // Host dashboard
        this.createdRoomViewModel = new CreatedRoomViewModel();
        this.createdRoomView = new CreatedRoomView(createdRoomViewModel);
        createdRoomView.setViewManagerModel(viewManagerModel);
        // Global update controller will be injected later
        cardPanel.add(createdRoomView, ViewManagerModel.CREATED_ROOM_VIEW);

        return this;
    }

    public AppBuilder addJoinAndCreateFlows() {
        // Create Room flow
        final CreateRoomViewModel createRoomViewModel = new CreateRoomViewModel();
        final CreateRoomPresenter createRoomPresenter = new CreateRoomPresenter(createRoomViewModel,
                createdRoomViewModel, viewManagerModel);
        final CreateRoomInputBoundary createRoomInteractor = new CreateRoomInteractor(userDataAccessObject,
                createRoomPresenter);
        final CreateRoomController createRoomController = new CreateRoomController(createRoomInteractor);
        final CreateRoomView createRoomView = new CreateRoomView(createRoomViewModel);
        createRoomView.setController(createRoomController);
        cardPanel.add(createRoomView, ViewManagerModel.CREATE_ROOM_VIEW);

        // Join Room flow
        final JoinRoomViewModel joinRoomViewModel = new JoinRoomViewModel();
        final JoinRoomPresenter joinRoomPresenter = new JoinRoomPresenter(joinRoomViewModel,
                this.createdRoomViewModel, viewManagerModel);
        final JoinRoomInputBoundary joinRoomInteractor = new JoinRoomInteractor(userDataAccessObject,
                joinRoomPresenter);
        final JoinRoomController joinRoomController = new JoinRoomController(joinRoomInteractor);
        final JoinRoomView joinRoomView = new JoinRoomView(joinRoomViewModel);
        joinRoomView.setJoinRoomController(joinRoomController);
        cardPanel.add(joinRoomView, ViewManagerModel.JOIN_ROOM_VIEW);

        return this;
    }

    public AppBuilder addShortlistView() {
        this.shortlistViewModel = new ShortlistViewModel();
        this.shortlistView = new ShortlistView(viewManagerModel, shortlistViewModel);
        cardPanel.add(shortlistView, ViewManagerModel.SHORTLIST_VIEW);
        return this;
    }

    public AppBuilder addVoteView() {
        this.voteViewModel = new interface_adapter.vote.VoteViewModel();
        this.voteView = new view.VoteView(this.voteViewModel);
        this.voteView.setViewManagerModel(viewManagerModel);
        cardPanel.add(voteView, ViewManagerModel.VOTE_VIEW);
        return this;
    }

    public AppBuilder addWinnerView() {
        // Create ViewModels and View
        this.winnerViewModel = new interface_adapter.winner.WinnerViewModel();
        this.winnerView = new WinnerView();

        // Use Factory pattern to create controller (encapsulates creation logic)
        final interface_adapter.winner.WinnerController winnerController = interface_adapter.winner.WinnerComponentFactory
                .createWinnerController(
                        userDataAccessObject, winnerViewModel);

        // Wire View to observe ViewModel changes (Observer pattern)
        wireWinnerViewToViewModel();

        // Wire controllers to views
        this.winnerView.setWinnerController(winnerController);
        if (this.voteView != null) {
            this.voteView.setOnComputeWinner(() -> handleComputeWinner(winnerController));
        }

        cardPanel.add(winnerView, ViewManagerModel.WINNER_VIEW);
        return this;
    }

    public AppBuilder addVoteUseCase() {
        // Use Factory pattern to create controller (encapsulates creation logic)
        final interface_adapter.vote.VoteController voteController = interface_adapter.vote.VoteComponentFactory
                .createVoteController(
                        userDataAccessObject, voteViewModel);

        // Wire the submit handler to the VoteView
        if (this.voteView != null) {
            this.voteView.setOnSubmit(rankedMovieIds -> {
                voteController.submitBallot(userDataAccessObject.getUsername(), rankedMovieIds);
            });
        }
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
        // If search view already exists, inject the same controller so "Add" from
        // search works
        if (this.searchView != null) {
            this.searchView.setAddMovieController(this.addMovieController);
        }
        return this;
    }

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
        UpdateRoomInputBoundary updateRoomInputBoundary = new UpdateRoomInteractor(
                userDataAccessObject,
                shortlistPresenter,
                createdRoomViewModel,
                voteViewModel,
                viewManagerModel);
        this.updateRoomController = new interface_adapter.shortlist.UpdateRoomController(
                updateRoomInputBoundary);
        shortlistView.setUpdateRoomController(this.updateRoomController);
        if (this.createdRoomView != null) {
            this.createdRoomView.setUpdateRoomController(this.updateRoomController);
        }
        if (this.voteView != null) {
            this.voteView.setUpdateRoomController(this.updateRoomController);
        }
        return this;
    }

    public AppBuilder addSearchUseCase() {
        // Create Search ViewModel and Presenter
        this.searchViewModel = new SearchViewModel();
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

        // Wire SearchViewModel to CreatedRoomView if it exists
        if (this.createdRoomView != null) {
            this.createdRoomView.setSearchViewModel(searchViewModel);
        }
        return this;
    }

    public AppBuilder addToggleLockRoomUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        ToggleLockRoomInputBoundary toggleLockRoomInputBoundary = new ToggleLockRoomInteractor(userDataAccessObject,
                shortlistPresenter);
        ToggleLockRoomController toggleLockRoomController = new ToggleLockRoomController(toggleLockRoomInputBoundary);
        shortlistView.setToggleLockRoomController(toggleLockRoomController);
        return this;
    }

    public AppBuilder addLeaveRoomUseCase() {
        LeaveRoomOutputBoundary leaveRoomPresenter = new LeaveRoomPresenter(createdRoomViewModel, viewManagerModel);
        LeaveRoomInputBoundary leaveRoomInputBoundary = new LeaveRoomInteractor(userDataAccessObject, leaveRoomPresenter);
        LeaveRoomController leaveRoomController = new LeaveRoomController(leaveRoomInputBoundary);
        createdRoomView.setLeaveRoomController(leaveRoomController);
        return this;
    }

    /**
     * Wire WinnerView to observe WinnerViewModel changes.
     * Implements Observer pattern - View reacts to ViewModel state changes.
     * Extracted method following "Extract Method" refactoring technique.
     */
    private void wireWinnerViewToViewModel() {
        winnerViewModel.addPropertyChangeListener(evt -> {
            interface_adapter.winner.WinnerState s = (interface_adapter.winner.WinnerState) evt.getNewValue();
            if (s != null) {
                winnerView.setWinnerTitle(s.getTitle());
                winnerView.setPoster(s.getPoster());
                winnerView.setDetails(s.getDetails());
            }
        });
    }

    /**
     * Handle winner computation workflow.
     * Extracted method following "Extract Method" refactoring technique.
     * 
     * @param winnerController controller to compute winner
     */
    private void handleComputeWinner(interface_adapter.winner.WinnerController winnerController) {
        // Host initiates winner computation
        winnerController.execute();

        // Trigger a global update so participants pick up winner
        if (updateRoomController != null) {
            updateRoomController.execute();
        }

        // Navigate to winner view
        viewManagerModel.setActiveViewName(ViewManagerModel.WINNER_VIEW);
    }

    public JFrame build() {
        final JFrame application = new JFrame("ReelRound");
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);
        viewManagerModel.setActiveViewName(ViewManagerModel.WELCOME_VIEW);
        return application;
    }
}
