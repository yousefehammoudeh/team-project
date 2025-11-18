package data_access.room;

import data_access.note_database.DataAccessException;

public class RoomDatabaseTest {
    public static void main(String[] args) {
        try{
            RoomDatabase roomDatabase = new RoomDatabase();
            roomDatabase.joinRoom("testRoomName12314");
//            roomDatabase.addMovie("1");
//            roomDatabase.addMovie("2");
            roomDatabase.removeMovie("3");
            System.out.println(roomDatabase.getMovieIDs());
            System.out.println(roomDatabase.getParticipantIDs());
        }
        catch (DataAccessException ex) {
            System.out.println("Exception: " + ex.getMessage());
        }
    }
}
