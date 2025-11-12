package use_case.remove_movie;

import java.util.List;

public interface RemoveMovieRoomDataAccessInterface {
    boolean isHost();

    boolean isLocked();

    boolean removeMovie(String movieID);

    List<String> getMovieIDs();
}
