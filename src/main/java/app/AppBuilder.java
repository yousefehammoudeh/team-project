package app;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import data_access.room.InMemoryRoomDataAccessObject;
import interface_adapter.shortlist.AddMovieController;
import interface_adapter.shortlist.RemoveMovieController;
import interface_adapter.shortlist.ShortlistPresenter;
import interface_adapter.shortlist.ShortlistViewModel;
import interface_adapter.vote.VotePresenter;
import interface_adapter.vote.VoteViewModel;
import interface_adapter.vote.VoteController;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.vote.VoteInteractor;
import view.ShortlistView;
import view.ViewManager;
import view.VoteView;
import javax.swing.*;
import java.awt.*;
import java.util.Arrays;
import java.util.Collections;

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
    private ViewManager viewManager = new ViewManager(cardPanel, cardLayout, viewManagerModel);

    private final RoomDatabase userDataAccessObject = new RoomDatabase();

    private ShortlistView shortlistView;
    private ShortlistViewModel shortlistViewModel;
    private ShortlistPresenter shortlistPresenter;

    public AppBuilder addShortlistView() {
        this.shortlistViewModel = new ShortlistViewModel();
        this.shortlistView = new ShortlistView(shortlistViewModel);
        cardPanel.add(shortlistView, shortlistView.getName());
        return this;
    }

    public AppBuilder addAddMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final AddMovieInputBoundary addMovieInputBoundary = new AddMovieInteractor(userDataAccessObject, shortlistPresenter);
        final AddMovieController addMovieController = new AddMovieController(addMovieInputBoundary);
        shortlistView.setAddMovieController(addMovieController);
        return this;
    }

    public AppBuilder addRemoveMovieUseCase() {
        if (shortlistPresenter == null) {
            shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        }
        final RemoveMovieInputBoundary removeMovieInputBoundary = new RemoveMovieInteractor(userDataAccessObject, shortlistPresenter);
        final RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieInputBoundary);
        shortlistView.setRemoveMovieController(removeMovieController);
        return this;
    }

    public void build() {

    }
}
