package entity;

import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class Movie {
    private final String id;
    private final String title;
    private final String year;
    private final String posterPath;
    private final List<String> genres;
    private final String language;
    private final double rating;

    public Movie(String id, String title, String year, String posterPath, List<String> genres, String language,
            double rating) {
        this.id = id;
        this.title = title;
        this.year = year;
        this.posterPath = posterPath;
        this.genres = genres == null ? Collections.emptyList() : Collections.unmodifiableList(genres);
        this.language = language;
        this.rating = rating;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getYear() {
        return year;
    }

    public String getPosterPath() {
        return posterPath;
    }

    public List<String> getGenres() {
        return genres;
    }

    public String getLanguage() {
        return language;
    }

    public double getRating() {
        return rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Movie))
            return false;
        Movie movie = (Movie) o;
        return Objects.equals(id, movie.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Movie{" +
                "id='" + id + '\'' +
                ", title='" + title + '\'' +
                ", year='" + year + '\'' +
                '}';
    }
}
