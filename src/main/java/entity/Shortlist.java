package entity;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;

public class Shortlist {
    private final List<String> movieIds;
    private boolean locked;

    public Shortlist() {
        this.movieIds = new ArrayList<>();
        this.locked = false;
    }

    public Shortlist(List<String> movieIds, boolean locked) {
        this.movieIds = new ArrayList<>();
        if (movieIds != null)
            this.movieIds.addAll(movieIds);
        this.locked = locked;
    }

    public List<String> getMovieIds() {
        return Collections.unmodifiableList(movieIds);
    }

    public boolean isLocked() {
        return locked;
    }

    public boolean addMovie(String movieId) {
        if (locked || movieId == null)
            return false;
        if (!movieIds.contains(movieId))
            return movieIds.add(movieId);
        return false;
    }

    public boolean removeMovie(String movieId) {
        if (locked || movieId == null)
            return false;
        return movieIds.remove(movieId);
    }

    public void lock() {
        this.locked = true;
    }

    public void unlock() {
        this.locked = false;
    }

    @Override
    public String toString() {
        return "Shortlist{" + "movieIds=" + movieIds + ", locked=" + locked + '}';
    }
}
