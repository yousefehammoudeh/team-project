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
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            roomDatabase.createRoom(roomName);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testCreateRoomDuplicate() {
        String userName = UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
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
        RoomDatabase roomDatabase1 = new RoomDatabase(userName1);
        RoomDatabase roomDatabase2 = new RoomDatabase(userName2);
        try {
            String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            roomDatabase1.createRoom(roomName);
            roomDatabase2.joinRoom(roomName);
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

    @Test
    public void testJoinNonexistentRoom() {
        String userName = UUID.randomUUID().toString();
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
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
        RoomDatabase roomDatabase = new RoomDatabase(userName);
        try {
            String roomName = UUID.randomUUID().toString() + UUID.randomUUID().toString();
            roomDatabase.createRoom(roomName);
            List<String> participants = roomDatabase.getParticipantIDs();

            Assertions.assertEquals(1, roomDatabase.participantsCount());
            Assertions.assertEquals(1, participants.size());
            Assertions.assertEquals(userName, participants.get(0));
        } catch (DataAccessException e) {
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
        } catch (DataAccessException e) {
            e.printStackTrace();
            Assertions.fail(e.getMessage());
        }
    }

}
