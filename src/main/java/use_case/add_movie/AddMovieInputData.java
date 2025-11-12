package use_case.add_movie;

public class AddMovieInputData {
    private String movieID;

    public AddMovieInputData(String movieID) {
        this.movieID = movieID;
    }

    public String getMovieID() {
        return movieID;
    }
}
