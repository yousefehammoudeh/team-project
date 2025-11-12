package data_access.room;

import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.shortlist.ShortlistOutputBoundary;

import java.util.ArrayList;
import java.util.List;

/**
 * TODO: In-memory gateway for prototyping all room-related data access.
 *
 * Responsibilities:
 * - Store rooms, participants, shortlist, ballots, filters
 * - Provide search/suggestions gateway surface (stubbed/mocked)
 */
public class InMemoryRoomDataAccessObject implements
        AddMovieRoomDataAccessInterface,
        RemoveMovieRoomDataAccessInterface {
    private final Room room = new Room("", "");

    public boolean isHost() {
        return true;
    }

    public boolean isLocked() {
        return room.isLocked();
    }

    public boolean addMovie(String movieID) {
        return room.addToShortlist(movieID);
    }

    public boolean removeMovie(String movieID) {
        return room.removeFromShortlist(movieID);
    }

    public List<String> getMovieIDs() {
        return new ArrayList<>(room.getShortlist().getMovieIds()); // return a new list to prevent modification
    }
}
