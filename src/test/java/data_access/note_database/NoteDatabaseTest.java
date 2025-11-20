package data_access.note_database;

import org.json.JSONException;

import java.io.IOException;


public class NoteDatabaseTest {
    private static final int SUCCESS_CODE = 200;
    private static final int CREDENTIAL_ERROR = 401;
    private static final String API_URL = "http://vm003.teach.cs.toronto.edu:20112/";
    private static final String CONTENT_TYPE_LABEL = "Content-Type";
    private static final String CONTENT_TYPE_JSON = "application/json";
    private static final String STATUS_CODE_LABEL = "status_code";
    private static final String USERNAME = "username";
    private static final String PASSWORD = "password";
    private static final String MESSAGE = "message";

    public static void main(String[] args) throws IOException, JSONException {
        String username = "testusername";
        String password = "kIM2hmkkT2RSraHnYARNtqxiY2YjignI";
        NoteDatabase noteDataAccessObject = new NoteDataAccessObject();
        try {
            password = noteDataAccessObject.getPassword(username);
            System.out.println(password);
            System.out.println(noteDataAccessObject.saveNote(username, password, "test note 123"));
            System.out.println(noteDataAccessObject.loadNote(username));
        }
        catch (DataAccessException ex) {
            System.out.println(ex.getMessage());
        }
    }
}
