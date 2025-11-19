package use_case.vote;

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
    boolean saveBallot(Ballot ballot);

    /** Return all currently submitted ballots. */
    List<Ballot> fetchBallots();

    /** Return the current shortlist movie ids (order matters for tie-breaking). */
    List<String> fetchShortlist();

    /** Return number of participants in the room. Used to show expected ballots. */
    int participantCount();

    /**
     * Check whether the given participantId is the host (used for privileged ops).
     */
    boolean isHostParticipant(String participantId);
}
