package shortlist;

import data_access.room.InMemoryRoomDataAccessObject;
import interface_adapter.shortlist.AddMovieController;
import interface_adapter.shortlist.RemoveMovieController;
import interface_adapter.shortlist.ShortlistPresenter;
import interface_adapter.shortlist.ShortlistViewModel;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.shortlist.ShortlistOutputBoundary;
import view.ShortlistView;

import javax.swing.*;

public class ShortlistViewTest {
    public static void main(String[] args) {

        ShortlistViewModel shortlistViewModel = new ShortlistViewModel();
        ShortlistView shortlistView = new ShortlistView(shortlistViewModel);

        InMemoryRoomDataAccessObject roomDataAccessObject = new InMemoryRoomDataAccessObject();

        ShortlistOutputBoundary shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        AddMovieInputBoundary addMovieInteractor = new AddMovieInteractor(roomDataAccessObject, shortlistPresenter);
        AddMovieController addMovieController = new AddMovieController(addMovieInteractor);
        shortlistView.setAddMovieController(addMovieController);

        RemoveMovieInputBoundary removeMovieInteractor = new RemoveMovieInteractor(roomDataAccessObject, shortlistPresenter);
        RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieInteractor);
        shortlistView.setRemoveMovieController(removeMovieController);

        JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(shortlistView);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
