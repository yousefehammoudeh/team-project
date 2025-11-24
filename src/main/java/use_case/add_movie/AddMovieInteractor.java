package use_case.add_movie;

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
        final String movieID = addMovieInputData.getMovieID();
        if (roomDataAccessObject.isLocked()) {
            shortlistPresenter.presentFailure("Shortlist is locked.");
        }
        else if (roomDataAccessObject.isHost()) {
            // Add directly and notify other users in the room.
            final boolean success = roomDataAccessObject.addMovie(movieID);
            if (success) {
                final ShortlistOutputData shortlistOutputData =
                        new ShortlistOutputData(roomDataAccessObject.getMovieIDs(), roomDataAccessObject.isLocked());
                shortlistPresenter.present(shortlistOutputData);
            }
            else {
                shortlistPresenter.presentFailure("Movie already in shortlist.");
            }
        }
        else {
            // TODO: notify host?
        }
    }
}
