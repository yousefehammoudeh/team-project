package entity;

import java.util.List;
import java.util.ArrayList;
import java.util.Objects;

public class Room {
    private final String code;
    private String hostId;
    private boolean locked;
    private final List<Participant> participants;
    private final List<String> shortlist;
    private final List<Ballot> ballots;
    private String selectedMovieId;

    public Room(String code, String hostId) {
        this.code = code;
        this.hostId = hostId;
        this.locked = false;
        this.participants = new ArrayList<>();
        this.shortlist = new ArrayList<>();
        this.ballots = new ArrayList<>();
    }

    public Room(String code, String hostId, boolean locked,
            List<Participant> participants, List<String> shortlist, List<Ballot> ballots) {
        this.code = code;
        this.hostId = hostId;
        this.locked = locked;
        this.participants = participants;
        this.shortlist = shortlist;
        this.ballots = ballots;
    }

    public String getCode() {
        return code;
    }

    public List<Participant> getParticipants() {
        return participants;
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

    public synchronized boolean addParticipant(Participant p) {
        if (getParticipants().stream().anyMatch(existing -> existing.getName().equals(p.getName()))) {
            return false;
        }
        boolean added = getParticipants().add(p);
        if (added && hostId == null) {
            hostId = p.getId();
        }
        return added;
    }

    public void removeParticipant(Participant p) {
        getParticipants().remove(p);
    }

    public synchronized boolean addToShortlist(String movieId) {
        if (shortlist.contains(movieId)) {
            return false;
        }
        return getShortlist().add(movieId);
    }

    public synchronized boolean removeFromShortlist(String movieId) {
        return getShortlist().remove(movieId);
    }

    public void lockShortlist(String token) {
        this.setLocked(true);
    }

    public void unlockShortlist(String token) {
        this.setLocked(false);
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
        if (!ballot.isValidForShortlist(this.getShortlist())) {
            return false;
        }
        // remove existing ballot for participant
        getBallots().removeIf(b -> Objects.equals(b.getParticipantId(), ballot.getParticipantId()));
        getBallots().add(ballot);
        return true;
    }

    public synchronized List<Ballot> getBallots() {
        return ballots;
    }

    public String getHostId() {
        return hostId;
    }

    public boolean isHostParticipant(String participantId) {
        return Objects.equals(this.hostId, participantId);
    }

    @Override
    public String toString() {
        return "Room{" + "code='" + code + '\'' + ", participants=" + getParticipants().size() + ", shortlist="
                + getShortlist()
                + '}';
    }

    public void setLocked(boolean locked) {
        this.locked = locked;
    }

}
