package entity;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

class RoomTest {
    @Test
    void testAddParticipant() {
        String roomCode = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();
        String userName = UUID.randomUUID().toString();
        Room room = new Room(roomCode, userID);

        Participant participant = new Participant(userID, userName);
        boolean added = room.addParticipant(participant);
        Assertions.assertTrue(added);
        List<Participant> participants = room.getParticipants();
        Assertions.assertEquals(1, participants.size());
        Assertions.assertEquals(userID, participants.get(0).getId());
        Assertions.assertEquals(userName, participants.get(0).getName());

        Participant sameParticipant = new Participant(userID, userName);
        boolean addedSame = room.addParticipant(sameParticipant);
        Assertions.assertFalse(addedSame);
        participants = room.getParticipants();
        Assertions.assertEquals(1, participants.size());
    }

    @Test
    void testIsHost() {
        String roomCode = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();
        Room room = new Room(roomCode, userID);
        Assertions.assertTrue(room.isHostParticipant(userID));

        String otherID = UUID.randomUUID().toString();
        Assertions.assertFalse(room.isHostParticipant(otherID));
    }

    @Test
    void testAddToShortlist() {
        String roomCode = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();
        Room room = new Room(roomCode, userID);
        List<String> shortlist = room.getShortlist();
        Assertions.assertEquals(0, shortlist.size());

        String movieID1 = UUID.randomUUID().toString();
        boolean added = room.addToShortlist(movieID1);
        Assertions.assertTrue(added);
        shortlist = room.getShortlist();
        Assertions.assertEquals(1, shortlist.size());
        Assertions.assertEquals(movieID1, shortlist.get(0));

        added = room.addToShortlist(movieID1);
        Assertions.assertFalse(added);
        shortlist = room.getShortlist();
        Assertions.assertEquals(1, shortlist.size());

        String movieID2 = UUID.randomUUID().toString();
        added = room.addToShortlist(movieID2);
        Assertions.assertTrue(added);
        Assertions.assertEquals(2, shortlist.size());
        Assertions.assertTrue(shortlist.contains(movieID1));
        Assertions.assertTrue(shortlist.contains(movieID2));
    }

    @Test
    void testRemoveFromShortlist() {
        String roomCode = UUID.randomUUID().toString();
        String userID = UUID.randomUUID().toString();
        Room room = new Room(roomCode, userID);

        String movieID1 = UUID.randomUUID().toString();
        room.addToShortlist(movieID1);

        boolean removed = room.removeFromShortlist(movieID1);
        Assertions.assertTrue(removed);
        List<String> shortlist = room.getShortlist();
        Assertions.assertEquals(0, shortlist.size());

        removed = room.removeFromShortlist(movieID1);
        Assertions.assertFalse(removed);
    }
}
