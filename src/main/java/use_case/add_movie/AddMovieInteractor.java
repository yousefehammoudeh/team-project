package use_case.add_movie;

import data_access.note_database.DataAccessException;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

public class AddMovieInteractor implements AddMovieInputBoundary {
    private final AddMovieRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;

    public AddMovieInteractor(AddMovieRoomDataAccessInterface roomDataAccessObject,
                              ShortlistOutputBoundary shortlistOutputBoundary) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistOutputBoundary;
    }

    public void execute(AddMovieInputData addMovieInputData) {
        try{
            final String movieID = addMovieInputData.getMovieID();
            if (roomDataAccessObject.isLocked()) {
                shortlistPresenter.presentFailure("The room is locked.");
            }
            else if (!roomDataAccessObject.isHost()) {
                shortlistPresenter.presentFailure("Only the host can add movies to the shortlist.");
            }
            else {
                final boolean success = roomDataAccessObject.addMovie(movieID);
                if (success) {
                    final ShortlistOutputData shortlistOutputData =
                            new ShortlistOutputData(roomDataAccessObject.getShortlist(), roomDataAccessObject.isLocked());
                    shortlistPresenter.present(shortlistOutputData);
                }
                else {
                    shortlistPresenter.presentFailure("The movie already exists.");
                }
            }
        }
        catch (DataAccessException ex) {
            shortlistPresenter.presentFailure(ex.getMessage());
        }
    }
}
