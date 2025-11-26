package data_access.room;

import data_access.note_database.DataAccessException;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Integration test for RoomDatabase.
 * NOTE: This test requires a live Note API connection and sets environment
 * variables.
 * Mark as @Disabled if running in CI without API access.
 */
@Disabled("Requires live Note API and manual environment setup")
public class RoomDatabaseWorkflowTest {

    private String testRoomCode;

    @BeforeEach
    public void setUp() {
        // Generate unique room code for each test run to avoid conflicts
        testRoomCode = "test_room_" + UUID.randomUUID().toString().substring(0, 8);
    }

    @Test
    public void testFullRoomWorkflow() throws DataAccessException, InterruptedException {
        // Step 1: Yousef creates the room
        System.out.println("=== Step 1: Yousef creates room ===");
        RoomDatabase yousefDB = new RoomDatabase("Yousef");
        yousefDB.createRoom(testRoomCode);

        // Validate: Room exists with Yousef as host and first participant
        assertTrue(yousefDB.isHost(), "Yousef should be host");
        assertEquals(1, yousefDB.participantsCount(), "Room should have 1 participant after creation");
        List<String> participants = yousefDB.getParticipantIDs();
        assertTrue(participants.contains("Yousef"), "Yousef should be in participants");
        assertEquals(0, yousefDB.getShortlist().size(), "Shortlist should be empty initially");
        System.out.println("✓ Room created. Host: Yousef, Participants: " + participants);
        System.out.println();

        Thread.sleep(1500); // Rate limit: wait between operations

        // Step 2: He Sun joins the room
        System.out.println("=== Step 2: He Sun joins ===");
        RoomDatabase heSunDB = new RoomDatabase("He Sun");
        heSunDB.joinRoom(testRoomCode);

        // Validate: He Sun is not host, participant count increased
        assertFalse(heSunDB.isHost(), "He Sun should not be host");
        assertEquals(2, heSunDB.participantsCount(), "Room should have 2 participants");
        participants = heSunDB.getParticipantIDs();
        assertTrue(participants.contains("He Sun"), "He Sun should be in participants");
        assertTrue(participants.contains("Yousef"), "Yousef should still be in participants");
        System.out.println("✓ He Sun joined. Participants: " + participants);
        System.out.println();

        Thread.sleep(1500);

        // Step 3: Diana joins the room
        System.out.println("=== Step 3: Diana joins ===");
        RoomDatabase dianaDB = new RoomDatabase("Diana");
        dianaDB.joinRoom(testRoomCode);

        // Validate: Diana is not host, participant count increased
        assertFalse(dianaDB.isHost(), "Diana should not be host");
        assertEquals(3, dianaDB.participantsCount(), "Room should have 3 participants");
        participants = dianaDB.getParticipantIDs();
        assertTrue(participants.contains("Diana"), "Diana should be in participants");
        System.out.println("✓ Diana joined. Participants: " + participants);
        System.out.println();

        // Step 4: Tamako joins the room
        System.out.println("=== Step 4: Tamako joins ===");
        RoomDatabase tamakoDB = new RoomDatabase("Tamako");
        tamakoDB.joinRoom(testRoomCode);

        // Validate: Tamako is not host, participant count increased
        assertFalse(tamakoDB.isHost(), "Tamako should not be host");
        assertEquals(4, tamakoDB.participantsCount(), "Room should have 4 participants");
        participants = tamakoDB.getParticipantIDs();
        assertTrue(participants.contains("Tamako"), "Tamako should be in participants");
        System.out.println("✓ Tamako joined. Participants: " + participants);
        System.out.println();

        // Step 5: Elaine joins the room
        System.out.println("=== Step 5: Elaine joins ===");
        RoomDatabase elaineDB = new RoomDatabase("Elaine");
        elaineDB.joinRoom(testRoomCode);

        // Validate: Elaine is not host, participant count increased
        assertFalse(elaineDB.isHost(), "Elaine should not be host");
        assertEquals(5, elaineDB.participantsCount(), "Room should have 5 participants");
        participants = elaineDB.getParticipantIDs();
        assertTrue(participants.contains("Elaine"), "Elaine should be in participants");
        System.out.println("✓ Elaine joined. Participants: " + participants);
        System.out.println();

        // Step 6: Yousef adds three movies to shortlist
        System.out.println("=== Step 6: Yousef adds movies ===");
        yousefDB = new RoomDatabase("Yousef");
        yousefDB.joinRoom(testRoomCode); // Rejoin to get fresh state

        boolean added1 = yousefDB.addMovie("tt0111161"); // The Shawshank Redemption
        boolean added2 = yousefDB.addMovie("tt0068646"); // The Godfather
        boolean added3 = yousefDB.addMovie("tt0468569"); // The Dark Knight

        // Validate: All three movies added successfully
        assertTrue(added1, "First movie should be added");
        assertTrue(added2, "Second movie should be added");
        assertTrue(added3, "Third movie should be added");
        List<String> shortlist = yousefDB.getShortlist();
        assertEquals(3, shortlist.size(), "Shortlist should have 3 movies");
        assertTrue(shortlist.contains("tt0111161"), "Shortlist should contain Shawshank");
        assertTrue(shortlist.contains("tt0068646"), "Shortlist should contain Godfather");
        assertTrue(shortlist.contains("tt0468569"), "Shortlist should contain Dark Knight");
        System.out.println("✓ Movies added. Shortlist: " + shortlist);
        System.out.println();

        // Step 7: Yousef changes his mind and replaces third movie
        System.out.println("=== Step 7: Yousef replaces third movie ===");
        boolean removed = yousefDB.removeMovie("tt0468569"); // Remove Dark Knight
        assertTrue(removed, "Third movie should be removed");

        boolean added4 = yousefDB.addMovie("tt0109830"); // Forrest Gump (replacement)
        assertTrue(added4, "Replacement movie should be added");

        // Validate: Shortlist now has original 2 + replacement
        shortlist = yousefDB.getShortlist();
        assertEquals(3, shortlist.size(), "Shortlist should still have 3 movies");
        assertTrue(shortlist.contains("tt0111161"), "Shortlist should still contain Shawshank");
        assertTrue(shortlist.contains("tt0068646"), "Shortlist should still contain Godfather");
        assertFalse(shortlist.contains("tt0468569"), "Shortlist should NOT contain Dark Knight");
        assertTrue(shortlist.contains("tt0109830"), "Shortlist should contain Forrest Gump");
        System.out.println("✓ Movie replaced. New shortlist: " + shortlist);
        System.out.println();

        // Step 8: Verify ALL users see the same room state
        System.out.println("=== Step 8: Cross-User Room State Verification ===");

        // Refresh each user's view of the room
        yousefDB.refreshRoom();
        heSunDB.refreshRoom();
        dianaDB.refreshRoom();
        tamakoDB.refreshRoom();
        elaineDB.refreshRoom();

        // Verify participant counts match across all users
        assertEquals(5, yousefDB.participantsCount(), "Yousef sees 5 participants");
        assertEquals(5, heSunDB.participantsCount(), "He Sun sees 5 participants");
        assertEquals(5, dianaDB.participantsCount(), "Diana sees 5 participants");
        assertEquals(5, tamakoDB.participantsCount(), "Tamako sees 5 participants");
        assertEquals(5, elaineDB.participantsCount(), "Elaine sees 5 participants");

        // Verify shortlist matches across all users
        List<String> yousefShortlist = yousefDB.getShortlist();
        List<String> heSunShortlist = heSunDB.getShortlist();
        List<String> dianaShortlist = dianaDB.getShortlist();
        List<String> tamakoShortlist = tamakoDB.getShortlist();
        List<String> elaineShortlist = elaineDB.getShortlist();

        assertEquals(yousefShortlist, heSunShortlist, "He Sun sees same shortlist as Yousef");
        assertEquals(yousefShortlist, dianaShortlist, "Diana sees same shortlist as Yousef");
        assertEquals(yousefShortlist, tamakoShortlist, "Tamako sees same shortlist as Yousef");
        assertEquals(yousefShortlist, elaineShortlist, "Elaine sees same shortlist as Yousef");
        assertEquals(3, yousefShortlist.size(), "Shortlist has 3 movies");

        // Verify participant lists match across all users
        List<String> yousefParticipants = yousefDB.getParticipantIDs();
        List<String> heSunParticipants = heSunDB.getParticipantIDs();
        List<String> dianaParticipants = dianaDB.getParticipantIDs();
        List<String> tamakoParticipants = tamakoDB.getParticipantIDs();
        List<String> elaineParticipants = elaineDB.getParticipantIDs();

        assertEquals(yousefParticipants, heSunParticipants, "He Sun sees same participants as Yousef");
        assertEquals(yousefParticipants, dianaParticipants, "Diana sees same participants as Yousef");
        assertEquals(yousefParticipants, tamakoParticipants, "Tamako sees same participants as Yousef");
        assertEquals(yousefParticipants, elaineParticipants, "Elaine sees same participants as Yousef");

        // Verify lock state matches across all users
        assertFalse(yousefDB.isLocked(), "Yousef sees room unlocked");
        assertFalse(heSunDB.isLocked(), "He Sun sees room unlocked");
        assertFalse(dianaDB.isLocked(), "Diana sees room unlocked");
        assertFalse(tamakoDB.isLocked(), "Tamako sees room unlocked");
        assertFalse(elaineDB.isLocked(), "Elaine sees room unlocked");

        // Verify host identification - each instance knows its own username
        assertTrue(yousefDB.isHost(), "Yousef knows he is host");
        assertFalse(heSunDB.isHost(), "He Sun knows he is not host");
        assertFalse(dianaDB.isHost(), "Diana knows she is not host");
        assertFalse(tamakoDB.isHost(), "Tamako knows she is not host");
        assertFalse(elaineDB.isHost(), "Elaine knows she is not host");

        // Print final state for manual verification
        System.out.println("✓ All users see consistent room state:");
        System.out.println("  Room Code: " + testRoomCode);
        System.out.println("  Participants: " + yousefParticipants);
        System.out.println("  Shortlist: " + yousefShortlist);
        System.out.println("  Locked: " + yousefDB.isLocked());
        System.out.println();

        System.out.println("✅ All validations passed!");
    }

    @Test
    public void testDuplicateMovieRejection() throws DataAccessException, InterruptedException {
        RoomDatabase db = new RoomDatabase("TestUser");
        String roomCode = "test_dup_" + UUID.randomUUID().toString().substring(0, 8);
        Thread.sleep(2000); // Wait before starting to avoid hitting previous test's quota
        db.createRoom(roomCode);

        // Add movie once
        boolean firstAdd = db.addMovie("tt1234567");
        assertTrue(firstAdd, "First add should succeed");

        // Try to add same movie again
        boolean secondAdd = db.addMovie("tt1234567");
        assertFalse(secondAdd, "Duplicate movie should be rejected");

        assertEquals(1, db.getShortlist().size(), "Shortlist should only have 1 movie");
    }
}
