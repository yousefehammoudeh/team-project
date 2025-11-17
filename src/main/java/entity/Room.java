package entity;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Room {
    private final String code;
    private final List<Participant> participants;
    private final List<String> shortlist;
    private final List<Ballot> ballots;
    private String hostId;
    private boolean locked;
    private String selectedMovieId;
    private ContentFilters contentFilters;

    public Room(String code) {
        this.code = code;
        this.participants = new ArrayList<>();
        this.shortlist = new ArrayList<>();
        this.ballots = new ArrayList<>();
        this.hostId = null;
        this.locked = false;
        this.selectedMovieId = null;
        this.contentFilters = ContentFilters.defaults();
    }

    public String getCode() {
        return code;
    }

    public List<Participant> getParticipants() {
        return Collections.unmodifiableList(participants);
    }

    public List<String> getShortlist() {
        return shortlist;
    }

    public boolean isLocked() {
        return locked;
    }

    public String getSelectedMovieId() {
        return selectedMovieId;
    }

    public ContentFilters getContentFilters() {
        return contentFilters;
    }

    public synchronized boolean addParticipant(Participant p) {
        if (p == null) {
            return false;
        }
        if (participants.stream().anyMatch(existing -> existing.getId().equals(p.getId()))) {
            return false;
        }
        boolean added = participants.add(p);
        if (added && hostId == null) {
            hostId = p.getId();
        }
        return added;
    }

    public synchronized boolean removeParticipantById(String participantId) {
        boolean removed = participants.removeIf(p -> Objects.equals(p.getId(), participantId));
        if (removed && Objects.equals(participantId, hostId)) {
            // promote next participant if any
            if (participants.isEmpty()) {
                hostId = null;
            }
            else {
                hostId = participants.get(0).getId();
            }
        }
        // remove any ballot the participant submitted
        ballots.removeIf(b -> Objects.equals(b.getParticipantId(), participantId));
        return removed;
    }

    public synchronized boolean addToShortlist(String movieId) {
        return shortlist.add(movieId);
    }

    public synchronized boolean removeFromShortlist(String movieId) {
        return shortlist.remove(movieId);
    }

    public void lockShortlist(String token) {
        this.locked = true;
    }

    public void unlockShortlist(String token) {
        this.locked = false;
    }

    /**
     * Select a movie without host checks (lower-level). Prefer selectMovieAsHost
     * for host-enforced selection.
     */
    public synchronized void selectMovie(String movieId) {
        this.selectedMovieId = movieId;
    }

    /**
     * Host-enforced selection of a movie.
     * 
     * @return true if selection succeeded
     */
    public synchronized boolean selectMovieAsHost(String movieId) {
        this.selectedMovieId = movieId;
        return true;
    }

    public void setContentFilters(ContentFilters filters) {
        if (filters != null)
            this.contentFilters = filters;
    }

    /**
     * Submit or replace a ballot for a participant. Validates against the current
     * shortlist.
     * 
     * @return true if accepted, false if invalid
     */
    public synchronized boolean submitBallot(Ballot ballot) {
        if (ballot == null) {
            return false;
        }
        if (!ballot.isValidForShortlist(this.shortlist)) {
            return false;
        }
        // remove existing ballot for participant
        ballots.removeIf(b -> Objects.equals(b.getParticipantId(), ballot.getParticipantId()));
        ballots.add(ballot);
        return true;
    }

    public synchronized List<Ballot> getBallots() {
        return Collections.unmodifiableList(new ArrayList<>(ballots));
    }

    public String getHostId() {
        return hostId;
    }

    public boolean isHostParticipant(String participantId) {
        return Objects.equals(this.hostId, participantId);
    }

    @Override
    public String toString() {
        return "Room{" + "code='" + code + '\'' + ", participants=" + participants.size() + ", shortlist=" + shortlist
                + '}';
    }
}
