package database;

import okhttp3.*;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

public class DatabaseTest {
    private static final String API_URL = "https://grade-apis.panchen.ca";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String MESSAGE = "message";
    private static final String STATUS_CODE = "status_code";
    private static final String TOKEN = "token";
    private static final String GRADE = "grade";
    private static final String COURSE = "course";
    private static final int SUCCESS_CODE = 200;
    public static String getAPIToken() {
        return System.getenv("GRADE_API_TOKEN");
    }

    public static void main(String[] args) {
        logData("testCourse");
    }

    public static void logData(String course) throws JSONException {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(APPLICATION_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put(COURSE, course);
        requestBody.put(GRADE, "test");
        final RequestBody body = RequestBody.create(mediaType, requestBody.toString());
        final Request request = new Request.Builder()
                .url(String.format("%s/grade", API_URL))
                .method("POST", body)
                .addHeader(TOKEN, getAPIToken())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println(responseBody);

            if (responseBody.getInt(STATUS_CODE) == SUCCESS_CODE) {
                return;
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    public static void getTeam() {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final Request request = new Request.Builder()
                .url(String.format("%s/team", API_URL))
                .method("GET", null)
                .addHeader(TOKEN, getAPIToken())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();
        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println(responseBody);

            if (responseBody.getInt(STATUS_CODE) == SUCCESS_CODE) {
                final JSONObject team = responseBody.getJSONObject("team");
                final JSONArray membersArray = team.getJSONArray("members");
                final String[] members = new String[membersArray.length()];
                for (int i = 0; i < membersArray.length(); i++) {
                    members[i] = membersArray.getString(i);
                }
            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    public static void leaveTeam() {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(APPLICATION_JSON);
        final JSONObject requestBody = new JSONObject();
        final RequestBody body = RequestBody.create(mediaType, requestBody.toString());
        final Request request = new Request.Builder()
                .url(String.format("%s/leaveTeam", API_URL))
                .method("PUT", body)
                .addHeader(TOKEN, getAPIToken())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println(responseBody);

            if (responseBody.getInt(STATUS_CODE) != SUCCESS_CODE) {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }

    public static void formTeam() {
        final OkHttpClient client = new OkHttpClient().newBuilder()
                .build();
        final MediaType mediaType = MediaType.parse(APPLICATION_JSON);
        final JSONObject requestBody = new JSONObject();
        requestBody.put("name", "csc207_TestRoomName12345");
        final RequestBody body = RequestBody.create(mediaType, requestBody.toString());
        final Request request = new Request.Builder()
                .url(String.format("%s/team", API_URL))
                .method("POST", body)
                .addHeader(TOKEN, getAPIToken())
                .addHeader(CONTENT_TYPE, APPLICATION_JSON)
                .build();

        try {
            final Response response = client.newCall(request).execute();
            final JSONObject responseBody = new JSONObject(response.body().string());

            System.out.println(responseBody);

            if (responseBody.getInt(STATUS_CODE) == SUCCESS_CODE) {
                final JSONObject team = responseBody.getJSONObject("team");
                final JSONArray membersArray = team.getJSONArray("members");
                final String[] members = new String[membersArray.length()];
                for (int i = 0; i < membersArray.length(); i++) {
                    members[i] = membersArray.getString(i);
                }

            }
            else {
                throw new RuntimeException(responseBody.getString(MESSAGE));
            }
        }
        catch (IOException | JSONException event) {
            throw new RuntimeException(event);
        }
    }
}
