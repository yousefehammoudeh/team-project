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
            // Cannot add if locked.
            // TODO: implement
        }
        else if (roomDataAccessObject.isHost()) {
            // Add directly and notify other users in the room.
            final boolean success = roomDataAccessObject.addMovie(movieID);
            if (success) {
                final ShortlistOutputData shortlistOutputData =
                        new ShortlistOutputData(roomDataAccessObject.getMovieIDs(), roomDataAccessObject.isLocked());
                shortlistPresenter.present(shortlistOutputData);
                // TODO: notify others
            }
            else {
                // TODO: error if the movie exists
            }
        }
        else {
            // If local user is not the host, notify the host to add.
            // TODO: implement
        }
    }
}
