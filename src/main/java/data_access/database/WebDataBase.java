package data_access.database;

import entity.Room;

public class WebDataBase implements RoomDataBase {
    private static final String API_URL = "https://grade-apis.panchen.ca";
    private static final String CONTENT_TYPE = "Content-Type";
    private static final String APPLICATION_JSON = "application/json";
    private static final String STATUS_CODE = "status_code";
    private static final String GRADE = "grade";
    private static final String MESSAGE = "message";
    private static final String NAME = "name";
    private static final String TOKEN = "GRADE_API_TOKEN";
    private static final String COURSE = "course";
    private static final String USERNAME = "username";
    private static final int SUCCESS_CODE = 200;

    public static String getAPIToken() {
        return System.getenv(TOKEN);
    }

    public static Room getRoom() {
        return null;
    }
}
