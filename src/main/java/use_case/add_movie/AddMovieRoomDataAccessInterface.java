package use_case.add_movie;

import java.util.List;

public interface AddMovieRoomDataAccessInterface {
    boolean isHost();

    boolean isLocked();

    boolean addMovie(String movieID);

    List<String> getMovieIDs();
}
