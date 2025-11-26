package view;

import data_access.room.RoomDatabase;
import interface_adapter.ViewManagerModel;
import interface_adapter.shortlist.*;
import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInteractor;
import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInteractor;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.update_room.UpdateRoomInputBoundary;
import use_case.update_room.UpdateRoomInteractor;

import javax.swing.*;

public class ShortlistViewTest {
    public static void main(String[] args) {
        createShortlistView("username", "testRoom123");
    }

    private static void createShortlistView(String username, String roomName) {
        ViewManagerModel viewManagerModel = new ViewManagerModel();
        ShortlistViewModel shortlistViewModel = new ShortlistViewModel();
        view.ShortlistView shortlistView = new view.ShortlistView(viewManagerModel, shortlistViewModel);

        RoomDatabase roomDataAccessObject = new RoomDatabase(username);
        try {
            roomDataAccessObject.joinRoom(roomName);
        }
        catch (Exception e) {
            e.printStackTrace();
        }

        ShortlistOutputBoundary shortlistPresenter = new ShortlistPresenter(shortlistViewModel);
        AddMovieInputBoundary addMovieInteractor = new AddMovieInteractor(roomDataAccessObject, shortlistPresenter);
        AddMovieController addMovieController = new AddMovieController(addMovieInteractor);
        shortlistView.setAddMovieController(addMovieController);

        RemoveMovieInputBoundary removeMovieInteractor =
                new RemoveMovieInteractor(roomDataAccessObject, shortlistPresenter);
        RemoveMovieController removeMovieController = new RemoveMovieController(removeMovieInteractor);
        shortlistView.setRemoveMovieController(removeMovieController);

        UpdateRoomInputBoundary updateRoomInputBoundary =
                new UpdateRoomInteractor(roomDataAccessObject, shortlistPresenter);
        UpdateRoomController updateRoomController = new UpdateRoomController(updateRoomInputBoundary);
        shortlistView.setUpdateRoomController(updateRoomController);

        viewManagerModel.setActiveViewName(shortlistView.getViewName());

        JFrame application = new JFrame();
        application.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        application.add(shortlistView);
        application.pack();
        application.setLocationRelativeTo(null);
        application.setVisible(true);
    }
}
