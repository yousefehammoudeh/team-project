package data_access.room;

import data_access.note_database.DataAccessException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

import static data_access.HTTPCode.CONFLICT_ERROR;
import static data_access.HTTPCode.NOT_FOUND_ERROR;

class RoomDatabaseTest {

    @Test
    public void testCreateRoom() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCreateRoomDuplicate() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }

        try {
            roomDatabase.createRoom(roomName);
        }
        catch (DataAccessException e) {
            Assertions.assertEquals(CONFLICT_ERROR, e.getCode());
        }
        catch (Exception e) {
            Assertions.fail("Created two rooms with the same room name");
        }
    }

    @Test
    public void testJoinRoom() {
        String userName1 = UUID.randomUUID().toString();
        String userName2 = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase1 = new RoomDatabase(userName1);
        RoomDatabase roomDatabase2 = new RoomDatabase(userName2);
        try {
            roomDatabase1.createRoom(roomName);
            roomDatabase2.joinRoom(roomName);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testJoinNonexistentRoom() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.joinRoom(roomName);
        }
        catch (DataAccessException e) {
            Assertions.assertEquals(NOT_FOUND_ERROR, e.getCode());
        }
        catch (Exception e) {
            Assertions.fail("Joined a room that does not exist");
        }
    }

    @Test
    public void testGetParticipantSelf() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
            List<String> participants = roomDatabase.getParticipantIDs();

            Assertions.assertEquals(1, roomDatabase.participantsCount());
            Assertions.assertEquals(1, participants.size());
            Assertions.assertEquals(userName, participants.get(0));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetParticipantsJoinRoom() {
        String userName1 = UUID.randomUUID().toString();
        String userName2 = UUID.randomUUID().toString();
        RoomDatabase roomDatabase1 = new RoomDatabase(userName1);
        RoomDatabase roomDatabase2 = new RoomDatabase(userName2);
        try {
            String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            roomDatabase1.createRoom(roomName);
            roomDatabase2.joinRoom(roomName);
            roomDatabase1.refreshRoom();
            List<String> participants1 = roomDatabase1.getParticipantIDs();
            List<String> participants2 = roomDatabase2.getParticipantIDs();

            Assertions.assertEquals(2, roomDatabase1.participantsCount());
            Assertions.assertEquals(2, roomDatabase2.participantsCount());
            Assertions.assertEquals(2, participants1.size());
            Assertions.assertEquals(2, participants2.size());
            Assertions.assertEquals(participants1, participants2);
            Assertions.assertTrue(participants1.contains(userName1));
            Assertions.assertTrue(participants1.contains(userName2));
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testAddMovie() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
            String movieID = UUID.randomUUID().toString();
            boolean addFirst = roomDatabase.addMovie(movieID);
            Assertions.assertTrue(addFirst);
            boolean addSecond = roomDatabase.addMovie(movieID);
            Assertions.assertFalse(addSecond);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testRemoveMovie() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
            String movieID = UUID.randomUUID().toString();
            boolean removeEmpty = roomDatabase.removeMovie(movieID);
            Assertions.assertFalse(removeEmpty);
            roomDatabase.addMovie(movieID);
            boolean removeFirst = roomDatabase.removeMovie(movieID);
            Assertions.assertTrue(removeFirst);
            boolean removeSecond = roomDatabase.removeMovie(movieID);
            Assertions.assertFalse(removeSecond);
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testGetShortlist() {
        String userName = UUID.randomUUID().toString();
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            roomDatabase.createRoom(roomName);
            List<String> shortlist = roomDatabase.getShortlist();
            Assertions.assertEquals(0, shortlist.size());

            String MovieID1 = UUID.randomUUID().toString();
            String MovieID2 = UUID.randomUUID().toString();
            roomDatabase.addMovie(MovieID1);
            shortlist = roomDatabase.getShortlist();
            Assertions.assertEquals(1, shortlist.size());
            Assertions.assertEquals(MovieID1, shortlist.get(0));

            roomDatabase.addMovie(MovieID2);
            shortlist = roomDatabase.getShortlist();
            Assertions.assertEquals(2, shortlist.size());
            Assertions.assertTrue(shortlist.contains(MovieID1));
            Assertions.assertTrue(shortlist.contains(MovieID2));

            roomDatabase.removeMovie(MovieID1);
            shortlist = roomDatabase.getShortlist();
            Assertions.assertEquals(1, shortlist.size());
            Assertions.assertEquals(MovieID2, shortlist.get(0));

            roomDatabase.removeMovie(MovieID2);
            shortlist = roomDatabase.getShortlist();
            Assertions.assertEquals(0, shortlist.size());
        }
        catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

}
