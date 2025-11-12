package interface_adapter.shortlist;

import use_case.add_movie.AddMovieInputBoundary;
import use_case.add_movie.AddMovieInputData;

public class AddMovieController {
    private final AddMovieInputBoundary addMovieInteractor;

    public AddMovieController(AddMovieInputBoundary addMovieInteractor) {
        this.addMovieInteractor = addMovieInteractor;
    }

    public void execute(String movieID) {
        final AddMovieInputData addMovieInputData = new AddMovieInputData(movieID);
        addMovieInteractor.execute(addMovieInputData);
    }
}
