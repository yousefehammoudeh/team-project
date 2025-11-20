package use_case.remove_movie;

import data_access.note_database.DataAccessException;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

public class RemoveMovieInteractor implements RemoveMovieInputBoundary {
    private final RemoveMovieRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;

    public RemoveMovieInteractor(RemoveMovieRoomDataAccessInterface roomDataAccessObject,
                                 ShortlistOutputBoundary shortlistOutputBoundary) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistOutputBoundary;
    }

    public void execute(RemoveMovieInputData removeMovieInputData) {
        try {
            final String movieID = removeMovieInputData.getMovieID();
            if (roomDataAccessObject.isLocked()) {
                // Cannot remove if locked.
                // TODO: implement
            }
            else if (roomDataAccessObject.isHost()) {
                // Remove directly and notify other users in the room.
                final boolean success = roomDataAccessObject.removeMovie(movieID);
                if (success) {
                    final ShortlistOutputData shortlistOutputData =
                            new ShortlistOutputData(roomDataAccessObject.getShortlist(), roomDataAccessObject.isLocked());
                    shortlistPresenter.present(shortlistOutputData);
                    // TODO: notify others
                }
                else {
                    // TODO: error if the movie exists
                }
            }
            else {
                // If local user is not the host, notify the host to remove.
                // TODO: implement
            }
        }
        catch (DataAccessException ex) {
            System.err.println("Error in RemoveMovieInteractor: " + ex.getMessage());
        }
    }
}
