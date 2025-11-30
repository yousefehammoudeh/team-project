package data_access.room;

import data_access.note_database.DataAccessException;
import data_access.note_database.NoteDataAccessObject;
import entity.Ballot;
import entity.Participant;
import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.create_room.CreateRoomUserDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.joined_room.JoinedRoomUserDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;
import use_case.toggle_lock_room.ToggleLockRoomDataAccessInterface;
import use_case.update_room.UpdateRoomDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomDatabase implements
        AddMovieRoomDataAccessInterface,
        RemoveMovieRoomDataAccessInterface,
        CreateRoomUserDataAccessInterface,
        VoteUserDataAccessInterface,
        JoinRoomUserDataAccessInterface,
        UpdateRoomDataAccessInterface,
        JoinedRoomUserDataAccessInterface,
        ToggleLockRoomDataAccessInterface {
    private static final String ROOM_NAME_HEADER = "csc207_tut0101group23_room_";

    private final NoteDataAccessObject noteDatabase = new NoteDataAccessObject();
    private String username;
    private Room room;

    /**
     * Create a RoomDatabase instance for the given user.
     * 
     * @param username The username of the current user accessing the room
     */
    public RoomDatabase(String username) {
        this.username = username;
    }

    public RoomDatabase() {
        this("");
    }

    private String getFormattedRoomCode() {
        return ROOM_NAME_HEADER + room.getCode();
    }

    public void refreshRoom() throws DataAccessException {
        checkRoomLoaded();
        String note = noteDatabase.loadNote(getFormattedRoomCode());
        room = RoomJSONParser.JSONToRoom(note);
    }

    private void saveRoom() throws DataAccessException {
        String note = RoomJSONParser.RoomToJSON(room);
        String roomCode = getFormattedRoomCode();
        String password = noteDatabase.getPassword(roomCode);
        noteDatabase.saveNote(roomCode, password, note);
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

    @Override
    public boolean isLocked() throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        return room.isLocked();
    }

    public void setLocked(boolean locked) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        room.setLocked(locked);
        saveRoom();
    }

    public boolean addMovie(String movieID) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        boolean result = room.addToShortlist(movieID);
        if (result) {
            saveRoom();
        }
        return result;
    }

    public boolean removeMovie(String movieID) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        boolean result = room.removeFromShortlist(movieID);
        if (result) {
            saveRoom();
        }
        return result;
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
        room = new Room(roomName, username);
        noteDatabase.register(getFormattedRoomCode());
        room.addParticipant(new Participant(username, username));
        saveRoom();
    }

    public boolean joinRoom(String roomName) throws DataAccessException {
        room = new Room(roomName, "");
        refreshRoom();
        boolean added = room.addParticipant(new Participant(username, username));
        if (added) {
            saveRoom();
        } else {
            // Do not join the room if a user with the same name exists
            room = null;
        }
        return added;
    }

    public int participantsCount() throws DataAccessException {
        checkRoomLoaded();
        return room.getParticipants().size();
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public boolean saveBallot(Ballot ballot) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        boolean result = room.submitBallot(ballot);
        if (result) {
            saveRoom();
        }
        return result;
    }

    public List<Ballot> getBallots() throws DataAccessException {
        checkRoomLoaded();
        return Collections.unmodifiableList(room.getBallots());
    }

    public boolean submitBallot(List<String> rankedMovieIds) throws DataAccessException {
        checkRoomLoaded();
        Ballot ballot = new Ballot(username, rankedMovieIds);
        refreshRoom();
        boolean result = room.submitBallot(ballot);
        if (result) {
            saveRoom();
        }
        return result;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getWinnerMovieId() throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        return room.getWinnerMovieId();
    }

    public void setWinnerMovieId(String movieId) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        room.setWinnerMovieId(movieId);
        saveRoom();
    }

    public void leaveRoom(String roomCode) throws DataAccessException {
        checkRoomLoaded();
        refreshRoom();
        room.removeParticipant(new Participant(username, username));
        saveRoom();
        room = null;
    }
}
