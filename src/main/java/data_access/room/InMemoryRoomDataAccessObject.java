package data_access.room;

import data_access.note_database.DataAccessException;
import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;

import entity.Ballot;
import entity.Participant;

import java.util.*;
import entity.Participant;
import static data_access.HTTPCode.CONFLICT_ERROR;
import static data_access.HTTPCode.NOT_FOUND_ERROR;

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
<<<<<<< HEAD
        VoteUserDataAccessInterface,
    JoinRoomUserDataAccessInterface {

    private Map<String, Room> rooms;
    private String username;
    private Room room;

    public InMemoryRoomDataAccessObject(String userName, Map<String, Room> rooms) {
        this.username = userName;
        this.rooms = rooms;
    }

    public InMemoryRoomDataAccessObject() {
        this("", new HashMap<>());
    }
=======
        VoteUserDataAccessInterface
         {
        private final Room room = new Room("c4a760", "");
>>>>>>> 19e83f2 (S02 use case - fixed the interactor to match room database)

    private void checkRoomLoaded() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
    }

    public boolean isHost() throws DataAccessException {
        checkRoomLoaded();
        return username.equals(room.getHostId());
    }

    public boolean isLocked() throws DataAccessException {
        checkRoomLoaded();
        return room.isLocked();
    }

    public boolean addMovie(String movieID) throws DataAccessException {
        checkRoomLoaded();
        return room.addToShortlist(movieID);
    }

    public boolean removeMovie(String movieID) throws DataAccessException {
        checkRoomLoaded();
        return room.removeFromShortlist(movieID);
    }

    public List<String> getShortlist() throws DataAccessException {
        checkRoomLoaded();
        return Collections.unmodifiableList(room.getShortlist());
    }

    public List<String> getParticipantIDs() throws DataAccessException {
        checkRoomLoaded();
        List<Participant> participants = room.getParticipants();
        List<String> participantIDs = new ArrayList<>();
        for (Participant p : participants) {
            participantIDs.add(p.getId());
        }
        return Collections.unmodifiableList(participantIDs);
    }

    public void createRoom(String roomName) throws DataAccessException {
        if (rooms.containsKey(roomName)) {
            throw new DataAccessException("Room " + roomName + " already exists.", CONFLICT_ERROR);
        }
        room = new Room(roomName, username);
        room.addParticipant(new Participant(username, username));
        rooms.put(roomName, room);
    }

    public boolean joinRoom(String roomCode) throws DataAccessException {
        if (!rooms.containsKey(roomCode)) {
            throw new DataAccessException("Room " + roomCode + " does not exist.", NOT_FOUND_ERROR);
        }
        room = rooms.get(roomCode);
        boolean added = room.addParticipant(new Participant(username, username));
        if (!added) {
            room = null;
        }
        return added;
    }

    public int participantsCount() throws DataAccessException {
        checkRoomLoaded();
        return room.getParticipants().size();
    }

    public boolean saveBallot(Ballot ballot) throws DataAccessException {
        checkRoomLoaded();
        return room.submitBallot(ballot);
    }

<<<<<<< HEAD
    public List<Ballot> getBallots() throws DataAccessException {
        checkRoomLoaded();
        return Collections.unmodifiableList(room.getBallots());
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
=======
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
>>>>>>> 19e83f2 (S02 use case - fixed the interactor to match room database)
    }
}
