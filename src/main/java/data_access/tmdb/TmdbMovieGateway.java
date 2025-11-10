package data_access.tmdb;

import entity.Movie;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import org.json.JSONArray;
import org.json.JSONObject;
import use_case.search.SearchUserDataAccessInterface;
import use_case.suggestions.SuggestionsUserDataAccessInterface;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class TmdbMovieGateway implements SearchUserDataAccessInterface,
        SuggestionsUserDataAccessInterface {

    private static final String DEFAULT_BASE = "https://api.themoviedb.org/3";
    private final String apiKey;
    private final String baseUrl;
    private final OkHttpClient client;

    public TmdbMovieGateway() {
        this(System.getenv("TMDB_API_KEY"), DEFAULT_BASE, new OkHttpClient());
    }

    public TmdbMovieGateway(String apiKey, String baseUrl, OkHttpClient client) {
        this.apiKey = apiKey;
        this.baseUrl = baseUrl == null ? DEFAULT_BASE : baseUrl;
        this.client = client == null ? new OkHttpClient() : client;
    }

    @Override
    public List<Movie> search(String query, entity.ContentFilters filters) throws IOException {
        requireApiKey();
        String q = query == null ? "" : java.net.URLEncoder.encode(query, java.nio.charset.StandardCharsets.UTF_8);
        String url = String.format("%s/search/movie?api_key=%s&query=%s", baseUrl, apiKey, q);
        if (filters != null) {
            url += "&include_adult=" + (filters.isExcludeAdult() ? "false" : "true");
            if (filters.getLanguage() != null && !filters.getLanguage().isBlank()) {
                url += "&language="
                        + java.net.URLEncoder.encode(filters.getLanguage(), java.nio.charset.StandardCharsets.UTF_8);
            }
        }

        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                return new ArrayList<>();
            String body = response.body().string();
            JSONObject json = new JSONObject(body);
            JSONArray results = json.optJSONArray("results");
            List<Movie> out = new ArrayList<>();
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    out.add(mapFromSearchResult(results.getJSONObject(i)));
                }
            }
            return out;
        }
    }

    @Override
    public Movie fetchDetails(String movieId, String appendToResponse) throws IOException {
        requireApiKey();
        String id = movieId == null ? "" : java.net.URLEncoder.encode(movieId, java.nio.charset.StandardCharsets.UTF_8);
        StringBuilder sb = new StringBuilder();
        sb.append(String.format("%s/movie/%s?api_key=%s", baseUrl, id, apiKey));
        if (appendToResponse != null && !appendToResponse.isBlank()) {
            sb.append("&append_to_response=")
                    .append(java.net.URLEncoder.encode(appendToResponse, java.nio.charset.StandardCharsets.UTF_8));
        }
        Request request = new Request.Builder().url(sb.toString()).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                return null;
            String body = response.body().string();
            JSONObject json = new JSONObject(body);
            return mapFromDetails(json);
        }
    }

    @Override
    public List<Movie> similarTo(String movieId) throws IOException {
        requireApiKey();
        String id = movieId == null ? "" : java.net.URLEncoder.encode(movieId, java.nio.charset.StandardCharsets.UTF_8);
        String url = String.format("%s/movie/%s/similar?api_key=%s", baseUrl, id, apiKey);
        Request request = new Request.Builder().url(url).get().build();
        try (Response response = client.newCall(request).execute()) {
            if (!response.isSuccessful() || response.body() == null)
                return new ArrayList<>();
            String body = response.body().string();
            JSONObject json = new JSONObject(body);
            JSONArray results = json.optJSONArray("results");
            List<Movie> out = new ArrayList<>();
            if (results != null) {
                for (int i = 0; i < results.length(); i++) {
                    out.add(mapFromSearchResult(results.getJSONObject(i)));
                }
            }
            return out;
        }
    }

    private void requireApiKey() throws IOException {
        if (this.apiKey == null || this.apiKey.isBlank()) {
            throw new IOException(
                    "TMDB API key is not set. Please set TMDB_API_KEY environment variable to the v3 API key.");
        }
    }

    private Movie mapFromSearchResult(JSONObject item) {
        String id = String.valueOf(item.optInt("id", -1));
        String title = item.optString("title", item.optString("name", ""));
        String release = item.optString("release_date", "");
        String year = release.length() >= 4 ? release.substring(0, 4) : "";
        String poster = item.optString("poster_path", "");
        String language = item.optString("original_language", "");
        double rating = item.optDouble("vote_average", 0.0);
        return new Movie(id, title, year, poster, null, language, rating);
    }

    private Movie mapFromDetails(JSONObject json) {
        String id = String.valueOf(json.optInt("id", -1));
        String title = json.optString("title", json.optString("name", ""));
        String release = json.optString("release_date", "");
        String year = release.length() >= 4 ? release.substring(0, 4) : "";
        String poster = json.optString("poster_path", "");
        String language = json.optString("original_language", "");
        double rating = json.optDouble("vote_average", 0.0);

        List<String> genres = new ArrayList<>();
        JSONArray gArr = json.optJSONArray("genres");
        if (gArr != null) {
            for (int i = 0; i < gArr.length(); i++) {
                JSONObject g = gArr.optJSONObject(i);
                if (g != null)
                    genres.add(g.optString("name", ""));
            }
        }

        return new Movie(id, title, year, poster, genres, language, rating);
    }

    public String buildPosterUrl(String posterPath, String size) {
        if (posterPath == null || posterPath.isBlank())
            return "";
        String s = (size == null || size.isBlank()) ? "w500" : size;
        String cleaned = posterPath.startsWith("/") ? posterPath : "/" + posterPath;
        return String.format("https://image.tmdb.org/t/p/%s%s", s, cleaned);
    }
}
