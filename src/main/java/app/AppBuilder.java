package app;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.toggle_lock_room.ToggleLockRoomInputBoundary;
import use_case.toggle_lock_room.ToggleLockRoomInteractor;
import use_case.update_room.UpdateRoomInputBoundary;
import use_case.update_room.UpdateRoomInteractor;
import view.ShortlistView;
import view.ViewManager;
import javax.swing.*;
import java.awt.*;

/**
 * TODO: Compose application wiring.
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

    public AppBuilder() {
        cardPanel.setLayout(cardLayout);
        // ViewManager will be used in future view switching logic
        new ViewManager(cardPanel, cardLayout, viewManagerModel);
    }

    public AppBuilder addShortlistView() {
        this.shortlistViewModel = new ShortlistViewModel();
        this.shortlistView = new ShortlistView(viewManagerModel, shortlistViewModel);
        cardPanel.add(shortlistView, shortlistView.getName());
        return this;
    }

    public AppBuilder addAddMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final AddMovieInputBoundary addMovieInputBoundary =
                new AddMovieInteractor(userDataAccessObject, shortlistPresenter);
        final AddMovieController addMovieController = new AddMovieController(addMovieInputBoundary);
        shortlistView.setAddMovieController(addMovieController);
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
                new UpdateRoomInteractor(userDataAccessObject,  shortlistPresenter);
        UpdateRoomController updateRoomController = new UpdateRoomController(updateRoomInputBoundary);
        shortlistView.setUpdateRoomController(updateRoomController);
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
        final JFrame application = new JFrame("APP TITLE"); // TODO: give a title
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(cardPanel);
        return application;
    }
}
