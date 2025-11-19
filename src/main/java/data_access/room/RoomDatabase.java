package data_access.room;

import data_access.note_database.DataAccessException;
import data_access.note_database.NoteDataAccessObject;
import data_access.note_database.NoteDatabase;
import entity.Ballot;
import entity.Participant;
import entity.Room;
import use_case.add_movie.AddMovieRoomDataAccessInterface;
import use_case.remove_movie.RemoveMovieRoomDataAccessInterface;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class RoomDatabase implements
        AddMovieRoomDataAccessInterface,
        RemoveMovieRoomDataAccessInterface {
    private static final String USERNAME = "NOTE_API_USERNAME";
    private static final String ROOM_NAME_HEADER = "csc207_group23_room_";

    private final NoteDatabase noteDatabase = new NoteDataAccessObject();
    private Room room;

    private String getFormattedRoomCode() {
        return ROOM_NAME_HEADER + room.getCode();
    }

    public void refreshRoom() throws DataAccessException {
        String note = noteDatabase.loadNote(getFormattedRoomCode());
        room = RoomJSONParser.JSONToRoom(note);
    }

    private void saveRoom() throws DataAccessException {
        String note = RoomJSONParser.RoomToJSON(room);
        String roomCode = getFormattedRoomCode();
        String password = noteDatabase.getPassword(roomCode);
        noteDatabase.saveNote(roomCode, password, note);
    }

    private static String getLocalUsername() {
        return System.getenv(USERNAME);
    }

    public boolean isHost() throws DataAccessException {
        refreshRoom();
        return getLocalUsername().equals(room.getHostId());
    }

    public boolean isLocked() throws DataAccessException {
        refreshRoom();
        return room.isLocked();
    }

    public boolean addMovie(String movieID) throws DataAccessException {
        refreshRoom();
        boolean result = room.addToShortlist(movieID);
        if (result) {
            saveRoom();
        }
        return result;
    }

    public boolean removeMovie(String movieID) throws DataAccessException {
        refreshRoom();
        boolean result = room.removeFromShortlist(movieID);
        if (result) {
            saveRoom();
        }
        return result;
    }

    public List<String> getShortlist() throws DataAccessException {
        refreshRoom();
        return Collections.unmodifiableList(room.getShortlist());
    }

    public List<String> getParticipantIDs() throws DataAccessException {
        refreshRoom();
        List<Participant> participants = room.getParticipants();
        List<String> participantIDs = new ArrayList<>();
        for (Participant p : participants) {
            participantIDs.add(p.getId());
        }
        return Collections.unmodifiableList(participantIDs);
    }

    public void createRoom(String roomName) throws DataAccessException {
        String username = getLocalUsername();
        room = new Room(roomName, username);
        noteDatabase.register(getFormattedRoomCode());
        room.addParticipant(new Participant(username, username));
        saveRoom();
    }

    public void joinRoom(String roomName) throws DataAccessException {
        room = new Room(roomName, "");
        refreshRoom();
        String username = getLocalUsername();
        room.addParticipant(new Participant(username, username));
        saveRoom();
    }

    public int participantsCount() throws DataAccessException {
        refreshRoom();
        return room.getParticipants().size();
    }

    public List<Ballot> getBallots() throws DataAccessException {
        refreshRoom();
        return Collections.unmodifiableList(room.getBallots());
    }
}
