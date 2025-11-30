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
                shortlistPresenter.presentFailure("The room is locked.");
            }
            else if (!roomDataAccessObject.isHost()) {
                shortlistPresenter.presentFailure("Only the host can remove movies from the shortlist.");
            }
            else {
                final boolean success = roomDataAccessObject.removeMovie(movieID);
                if (success) {
                    final ShortlistOutputData shortlistOutputData =
                            new ShortlistOutputData(roomDataAccessObject.getShortlist(), roomDataAccessObject.isLocked());
                    shortlistPresenter.present(shortlistOutputData);
                }
                else {
                    shortlistPresenter.presentFailure("The movie is not in the shortlist.");
                }
            }
        }
        catch (DataAccessException ex) {
            shortlistPresenter.presentFailure(ex.getMessage());
        }
    }
}
