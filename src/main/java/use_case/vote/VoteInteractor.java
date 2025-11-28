package use_case.vote;

import data_access.note_database.DataAccessException;
import entity.Ballot;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Implements vote submission and winner computation.
 *
 * Clean Architecture roles and comments:
 * - Controller -> constructs VoteInputData and calls submitBallot
 * - Interactor -> validates and persists ballots via gateway, computes winner
 * - Presenter -> formats results for the view model
 */
public class VoteInteractor implements VoteInputBoundary {
    private final VoteUserDataAccessInterface gateway;
    private final VoteOutputBoundary presenter;

    public VoteInteractor(VoteUserDataAccessInterface gateway,
            VoteOutputBoundary presenter) {
        this.gateway = gateway;
        this.presenter = presenter;
    }

    @Override
    public void submitBallot(VoteInputData inputData) {
        if (inputData == null) {
            presenter.presentFailure("Invalid ballot input");
            return;
        }
        try {
            Ballot ballot = inputData.toBallot();
            // Validate against current shortlist via the Room gateway
            List<String> shortlist = gateway.getShortlist();
            if (!ballot.isValidForShortlist(shortlist)) {
                presenter.presentFailure("Ballot invalid for current shortlist");
                return;
            }
            boolean saved = gateway.saveBallot(ballot);
            if (!saved) {
                presenter.presentFailure("Failed to save ballot");
                return;
            }
            // Present a lightweight update (no winner yet)
            int ballotsReceived = gateway.getBallots().size();
            VoteOutputData out = new VoteOutputData(null, new HashMap<>(), ballotsReceived, shortlist.size());
            presenter.present(out);
        } catch (DataAccessException e) {
            presenter.presentFailure("Database error: " + e.getMessage());
        }
    }

    @Override
    public void computeWinner(String hostId) {
        try {
            if (!gateway.isHost()) {
                presenter.presentFailure("Only host may compute winner");
                return;
            }
            List<String> shortlist = gateway.getShortlist();
            List<Ballot> ballots = gateway.getBallots();
            int n = shortlist.size();
            Map<String, Integer> scores = new HashMap<>();
            // initialize scores
            for (String id : shortlist) {
                scores.put(id, 0);
            }
            // Borda count: first choice gets n points, second gets n-1, ..., last gets 1
            // point
            // The ranked list order is: first element = rank 1 (highest), last = lowest
            // rank
            for (Ballot b : ballots) {
                List<String> ranked = b.getRankedMovieIds();
                for (int i = 0; i < ranked.size(); i++) {
                    String movieId = ranked.get(i);
                    if (!scores.containsKey(movieId))
                        continue; // skip outdated ids
                    int points = n - i; // First item (i=0) gets n points, second (i=1) gets n-1, etc.
                    scores.put(movieId, scores.get(movieId) + points);
                }
            }
            // choose highest score; tie-breaker: earliest in shortlist
            String winner = null;
            int best = Integer.MIN_VALUE;
            for (String id : shortlist) {
                int sc = scores.getOrDefault(id, 0);
                if (sc > best) {
                    best = sc;
                    winner = id;
                }
            }

            // Debug output
            System.out.println("=== Vote Results ===");
            System.out.println("Ballots received: " + ballots.size());
            for (Ballot b : ballots) {
                System.out.println("  " + b.getParticipantId() + ": " + b.getRankedMovieIds());
            }
            System.out.println("Scores:");
            for (String id : shortlist) {
                System.out.println("  " + id + ": " + scores.get(id) + " pts");
            }
            System.out.println("Winner: " + winner + " with " + best + " pts");
            System.out.println("==================");

            VoteOutputData out = new VoteOutputData(winner, scores, ballots.size(), n);
            presenter.present(out);
        } catch (DataAccessException e) {
            presenter.presentFailure("Database error: " + e.getMessage());
        }
    }
}
