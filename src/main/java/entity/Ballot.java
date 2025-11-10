package entity;

import java.util.List;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Objects;

public class Ballot {
    private final String participantId;
    private final List<String> rankedMovieIds;

    public Ballot(String participantId, List<String> rankedMovieIds) {
        this.participantId = participantId;
        this.rankedMovieIds = rankedMovieIds == null ? new ArrayList<>() : new ArrayList<>(rankedMovieIds);
    }

    public String getParticipantId() {
        return participantId;
    }

    public List<String> getRankedMovieIds() {
        return Collections.unmodifiableList(rankedMovieIds);
    }

    /**
     * Quick validation helper: ensure there are no duplicate movie ids and that
     * all movie ids are contained in the provided shortlist.
     */
    public boolean isValidForShortlist(Shortlist shortlist) {
        if (shortlist == null)
            return false;
        List<String> allowed = shortlist.getMovieIds();
        // check duplicates
        java.util.Set<String> seen = new java.util.HashSet<>();
        for (String id : rankedMovieIds) {
            if (id == null)
                return false;
            if (!allowed.contains(id))
                return false;
            if (!seen.add(id))
                return false; // duplicate
        }
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o)
            return true;
        if (!(o instanceof Ballot))
            return false;
        Ballot ballot = (Ballot) o;
        return Objects.equals(participantId, ballot.participantId)
                && Objects.equals(rankedMovieIds, ballot.rankedMovieIds);
    }

    @Override
    public int hashCode() {
        return Objects.hash(participantId, rankedMovieIds);
    }

    @Override
    public String toString() {
        return "Ballot{" + "participantId='" + participantId + '\'' + ", rankedMovieIds=" + rankedMovieIds + '}';
    }
}
