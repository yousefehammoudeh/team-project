package data_access.note_database;

public interface NoteDatabase {
    String register(String username) throws DataAccessException;

    String getPassword(String username) throws DataAccessException;

    String saveNote(String username, String password, String note) throws DataAccessException;

    String loadNote(String username) throws DataAccessException;
}
