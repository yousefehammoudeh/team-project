package data_access.note_database;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.UUID;

class NoteDataAccessObjectTest {
    @Test
    public void testRegister() {
        NoteDataAccessObject noteDataAccessObject = new NoteDataAccessObject();
        String username = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        try {
            String password = noteDataAccessObject.register(username);
            Assertions.assertEquals(password, noteDataAccessObject.getPassword(username));
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testLoadSave() {
        NoteDataAccessObject noteDataAccessObject = new NoteDataAccessObject();
        String username = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        try {
            String password = noteDataAccessObject.register(username);
            String note = UUID.randomUUID().toString();
            noteDataAccessObject.saveNote(username, password, note);
            Assertions.assertEquals(note, noteDataAccessObject.loadNote(username));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }
}
