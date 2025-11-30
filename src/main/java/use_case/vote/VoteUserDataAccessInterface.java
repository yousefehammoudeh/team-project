package use_case.vote;

import data_access.note_database.DataAccessException;
import entity.Ballot;

import java.util.List;

/**
 * Gateway for ballots persistence and room state queries used by voting.
 *
 * Implementations should be part of the data access layer and provide thread
 * safe access to ballots and shortlist.
 */
public interface VoteUserDataAccessInterface {
    /** Save or replace a ballot for a participant. */
    boolean saveBallot(Ballot ballot) throws DataAccessException;

    /** Return all currently submitted ballots. */
    List<Ballot> getBallots() throws DataAccessException;

    /** Return the current shortlist movie ids (order matters for tie-breaking). */
    List<String> getShortlist() throws DataAccessException;

    /** Return number of participants in the room. Used to show expected ballots. */
    int participantsCount() throws DataAccessException;

    /** Is current user host (privileged ops). */
    boolean isHost() throws DataAccessException;

    /** Is shortlist locked (voting allowed only when locked). */
    boolean isShortlistLocked() throws DataAccessException;
}
