package interface_adapter.shortlist;

import use_case.remove_movie.RemoveMovieInputBoundary;
import use_case.remove_movie.RemoveMovieInputData;

public class RemoveMovieController {
    private final RemoveMovieInputBoundary removeMovieInteractor;

    public RemoveMovieController(RemoveMovieInputBoundary removeMovieInteractor) {
        this.removeMovieInteractor = removeMovieInteractor;
    }

    public void execute(String movieID) {
        final RemoveMovieInputData removeMovieInputData = new RemoveMovieInputData(movieID);
        removeMovieInteractor.execute(removeMovieInputData);
    }
}
