package use_case.remove_movie;

public class RemoveMovieInputData {
    private String movieID;

    public RemoveMovieInputData(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieID() {
        return movieID;
    }
}
