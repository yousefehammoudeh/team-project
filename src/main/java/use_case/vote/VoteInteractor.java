package use_case.vote;

import data_access.note_database.DataAccessException;
import entity.Ballot;

import java.util.HashMap;
import java.util.List;

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
            if (!gateway.isLocked()) {
                presenter.presentFailure("Shortlist not locked; voting disabled");
                return;
            }
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
            int participantCount = gateway.participantsCount();
            // Current user just voted, so mark them as having voted
            VoteOutputData out = new VoteOutputData(null, new HashMap<>(), ballotsReceived, participantCount, true);
            presenter.present(out);
        } catch (DataAccessException e) {
            presenter.presentFailure("Database error: " + e.getMessage());
        }
    }
}
