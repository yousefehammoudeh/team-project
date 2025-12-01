package use_case.update_room;

import data_access.note_database.DataAccessException;

import java.util.List;

public interface UpdateRoomDataAccessInterface {
    public void refreshRoom() throws DataAccessException;

    public List<String> getShortlist() throws DataAccessException;

    public boolean isLocked() throws DataAccessException;

    public List<String> getParticipantIDs() throws DataAccessException;

    public int participantsCount() throws DataAccessException;

    public java.util.List<entity.Ballot> getBallots() throws DataAccessException;

    public String getUsername() throws DataAccessException;

    public boolean isHost() throws DataAccessException;

    public String getHostId() throws DataAccessException;

    /** If a winner was computed and persisted, return its movie id, else null. */
    public String getWinnerMovieId() throws DataAccessException;
}
