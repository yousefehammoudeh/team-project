package data_access.room;

import data_access.note_database.DataAccessException;
import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.joined_room.JoinedRoomUserDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;

import entity.Ballot;

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
        VoteUserDataAccessInterface,
        JoinRoomUserDataAccessInterface,
        JoinedRoomUserDataAccessInterface {

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

    public boolean isHost() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return username.equals(room.getHostId());
    }

    public boolean isLocked() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return room.isLocked();
    }

    public boolean addMovie(String movieID) throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return room.addToShortlist(movieID);
    }

    public boolean removeMovie(String movieID) throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return room.removeFromShortlist(movieID);
    }

    public List<String> getShortlist() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return Collections.unmodifiableList(room.getShortlist());
    }

    public List<String> getParticipantIDs() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
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

    public int participantCount() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return room.getParticipants().size();
    }

    public boolean saveBallot(Ballot ballot) {
        return room.submitBallot(ballot);
    }

    public List<Ballot> getBallots() throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        return Collections.unmodifiableList(room.getBallots());
    }

    public boolean submitBallot(List<String> rankedMovieIds) throws DataAccessException {
        if (room == null) {
            throw new DataAccessException("Room not loaded. Call createRoom() or joinRoom() first.");
        }
        Ballot ballot = new Ballot(username, rankedMovieIds);
        boolean result = room.submitBallot(ballot);
        return result;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
