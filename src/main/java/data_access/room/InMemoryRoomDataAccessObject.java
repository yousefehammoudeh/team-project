package data_access.room;

import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.joined_room.JoinedRoomUserDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;

import entity.Ballot;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import entity.Participant;

/**
 * TODO: In-memory gateway for prototyping all room-related data access.
 *
 * Responsibilities:
 * - Store rooms, participants, shortlist, ballots, filters
 * - Provide search/suggestions gateway surface (stubbed/mocked)
 */
public class InMemoryRoomDataAccessObject implements
        AddMovieRoomDataAccessInterface,
        RemoveMovieRoomDataAccessInterface,
        VoteUserDataAccessInterface,
        JoinRoomUserDataAccessInterface,
        JoinedRoomUserDataAccessInterface {
    private final Room room = new Room("c4a760", "");
    //private final Participant participant = new Participant("Alice");

    public InMemoryRoomDataAccessObject() {
        room.addParticipant(participant);
    }

    public boolean isHost() {
        return true;
    }

    public boolean isLocked() {
        return room.isLocked();
    }

    public boolean addMovie(String movieID) {
        return room.addToShortlist(movieID);
    }

    public boolean removeMovie(String movieID) {
        return room.removeFromShortlist(movieID);
    }

    public List<String> getShortlist() {
        return new ArrayList<>(room.getShortlist()); // return a new list to prevent modification
    }

    // --- Vote gateway implementations ---
    public boolean saveBallot(Ballot ballot) {
        return room.submitBallot(ballot);
    }

    public java.util.List<Ballot> fetchBallots() {
        return room.getBallots();
    }

    public java.util.List<String> fetchShortlist() {
        return new ArrayList<>(room.getShortlist());
    }

    public int participantCount() {
        return room.getParticipants().size();
    }

    public boolean isHostParticipant(String participantId) {
        return room.isHostParticipant(participantId);
    }

    /**
     * Test / composition helper: add a participant to the room. Returns true
     * if successfully added. This lets tests set the host.
     */
    public boolean addParticipant(String id, String name) {
        return room.addParticipant(new Participant(id, name));
    }

    public boolean addParticipant(String name) {
        return room.addParticipant(new Participant(name));
    }

    public void removeParticipant(String name) {
        room.removeParticipant(new Participant(name));
    }

    public boolean joinRoom(String roomCode) {
        return room.getCode().equals(roomCode);
    }

    public List<String> getParticipantIDs() {
        List<Participant> p = room.getParticipants();
        List<String> pID = new ArrayList<>();
        for (Participant participant : p) {
            pID.add(participant.getName());
        }

        return pID;
    }
}
