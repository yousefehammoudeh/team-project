package data_access.note_database;

import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.util.UUID;

public class NoteDataAccessObjectTest {
    @Test
    public void testRegister() throws IOException {
        NoteDataAccessObject noteDataAccessObject = new NoteDataAccessObject();
        String username = UUID.randomUUID().toString();
        try {
            String password = noteDataAccessObject.register(username);
            System.out.println(username);
            System.out.println(password);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
    }
}
