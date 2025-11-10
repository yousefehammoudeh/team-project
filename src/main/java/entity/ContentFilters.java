package entity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ContentFilters {
    private final boolean excludeAdult;
    private final double minRating;
    private final String language;
    private final List<String> includeGenres;
    private final List<String> excludeGenres;

    public ContentFilters(boolean excludeAdult, double minRating, String language, List<String> includeGenres,
            List<String> excludeGenres) {
        this.excludeAdult = excludeAdult;
        this.minRating = minRating;
        this.language = language;
        this.includeGenres = includeGenres == null ? new ArrayList<>() : new ArrayList<>(includeGenres);
        this.excludeGenres = excludeGenres == null ? new ArrayList<>() : new ArrayList<>(excludeGenres);
    }

    public boolean isExcludeAdult() {
        return excludeAdult;
    }

    public double getMinRating() {
        return minRating;
    }

    public String getLanguage() {
        return language;
    }

    public List<String> getIncludeGenres() {
        return Collections.unmodifiableList(includeGenres);
    }

    public List<String> getExcludeGenres() {
        return Collections.unmodifiableList(excludeGenres);
    }

    public static ContentFilters defaults() {
        return new ContentFilters(true, 0.0, "en-US", null, null);
    }
}
