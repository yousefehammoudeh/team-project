package view;

import data_access.room.InMemoryRoomDataAccessObject;
import data_access.room.RoomDatabase;
import interface_adapter.shortlist.AddMovieController;
import interface_adapter.shortlist.RemoveMovieController;
import interface_adapter.shortlist.ShortlistPresenter;
import interface_adapter.shortlist.ShortlistViewModel;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.shortlist.ShortlistOutputBoundary;

import javax.swing.*;

public class ShortlistViewTest {
    public static void main(String[] args) {

        ShortlistViewModel shortlistViewModel = new ShortlistViewModel();
        view.ShortlistView shortlistView = new view.ShortlistView(shortlistViewModel);

//        InMemoryRoomDataAccessObject roomDataAccessObject = new InMemoryRoomDataAccessObject();
        RoomDatabase roomDataAccessObject = new RoomDatabase("testusername");
        try {
            roomDataAccessObject.createRoom("qu3wrboqwe3");
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ShortlistOutputBoundary shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        AddMovieInputBoundary addMovieInteractor = new AddMovieInteractor(roomDataAccessObject, shortlistPresenter);
        AddMovieController addMovieController = new AddMovieController(addMovieInteractor);
        shortlistView.setAddMovieController(addMovieController);

        RemoveMovieInputBoundary removeMovieInteractor = new RemoveMovieInteractor(roomDataAccessObject,
                shortlistPresenter);
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
