package data_access.room;

import data_access.note_database.DataAccessException;
import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.joined_room.JoinedRoomUserDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.toggle_lock_room.ToggleLockRoomDataAccessInterface;
import use_case.update_room.UpdateRoomDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;

import entity.Ballot;
import entity.Participant;
import java.util.*;
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
        VoteUserDataAccessInterface,
        JoinRoomUserDataAccessInterface,
        JoinedRoomUserDataAccessInterface,
        ToggleLockRoomDataAccessInterface,
        UpdateRoomDataAccessInterface {

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

    private void checkRoomLoaded() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Create or join a room first.");
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

    public void setLocked(boolean locked) throws DataAccessException {
        checkRoomLoaded();
        room.setLocked(locked);
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

    // Support vote demo/tests without changing production interfaces
    public boolean addParticipant(String id, String name) {
        if (room == null) {
            room = new Room("demo", "");
        }
        return room.addParticipant(new Participant(id, name));
    }

    public boolean saveBallot(Ballot ballot) throws DataAccessException {
        checkRoomLoaded();
        return room.submitBallot(ballot);
    }

    public List<Ballot> getBallots() throws DataAccessException {
        checkRoomLoaded();
        return Collections.unmodifiableList(room.getBallots());
    }

    public String getWinnerMovieId() throws DataAccessException {
        checkRoomLoaded();
        return room.getWinnerMovieId();
    }

    public void setWinnerMovieId(String movieId) throws DataAccessException {
        checkRoomLoaded();
        room.setWinnerMovieId(movieId);
    }

    @Override
    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void leaveRoom(String roomCode) {
        try {
            checkRoomLoaded();
            // Stub: handled by another implementation/PR
        } catch (DataAccessException ignored) {
        }
    }

    @Override
    public void refreshRoom() throws DataAccessException {
        // No-op for in-memory implementation - room is always fresh
        checkRoomLoaded();
    }
}
