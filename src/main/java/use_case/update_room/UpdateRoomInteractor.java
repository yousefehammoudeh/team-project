package use_case.update_room;

import data_access.note_database.DataAccessException;
import entity.Ballot;
import entity.Movie;
import interface_adapter.ViewManagerModel;
import interface_adapter.created_room.CreatedRoomState;
import interface_adapter.created_room.CreatedRoomViewModel;
import interface_adapter.vote.VoteState;
import interface_adapter.vote.VoteViewModel;
import use_case.shortlist.ShortlistOutputBoundary;
import use_case.shortlist.ShortlistOutputData;

import java.util.ArrayList;
import java.util.List;

public class UpdateRoomInteractor implements UpdateRoomInputBoundary {
    UpdateRoomDataAccessInterface roomDataAccessObject;
    private final ShortlistOutputBoundary shortlistPresenter;
    private final CreatedRoomViewModel createdRoomViewModel;
    private final VoteViewModel voteViewModel;
    private final data_access.tmdb.TmdbMovieGateway tmdbGateway;
    private final ViewManagerModel viewManagerModel;

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
            ShortlistOutputBoundary shortlistPresenter,
            CreatedRoomViewModel createdRoomViewModel,
            VoteViewModel voteViewModel) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
        this.createdRoomViewModel = createdRoomViewModel;
        this.voteViewModel = voteViewModel;
        this.tmdbGateway = new data_access.tmdb.TmdbMovieGateway();
        this.viewManagerModel = null;
    }

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
            ShortlistOutputBoundary shortlistPresenter) {
        this(roomDataAccessObject, shortlistPresenter, null, null, null);
    }

    public UpdateRoomInteractor(UpdateRoomDataAccessInterface roomDataAccessObject,
            ShortlistOutputBoundary shortlistPresenter,
            CreatedRoomViewModel createdRoomViewModel,
            VoteViewModel voteViewModel,
            ViewManagerModel viewManagerModel) {
        this.roomDataAccessObject = roomDataAccessObject;
        this.shortlistPresenter = shortlistPresenter;
        this.createdRoomViewModel = createdRoomViewModel;
        this.voteViewModel = voteViewModel;
        this.tmdbGateway = new data_access.tmdb.TmdbMovieGateway();
        this.viewManagerModel = viewManagerModel;
    }

    public void execute() {
        try {
            roomDataAccessObject.refreshRoom();
            final ShortlistOutputData shortlistOutputData = new ShortlistOutputData(roomDataAccessObject.getShortlist(),
                    roomDataAccessObject.isLocked());
            shortlistPresenter.present(shortlistOutputData);

            // Update Host dashboard participants and lock
            if (createdRoomViewModel != null) {
                CreatedRoomState hostState = createdRoomViewModel.getState();
                hostState.setParticipants(roomDataAccessObject.getParticipantIDs());
                hostState.setError(null);
                createdRoomViewModel.firePropertyChanged();
            }

            // Update Vote view with shortlist movies and poster URLs
            if (voteViewModel != null) {
                List<String> shortlist = roomDataAccessObject.getShortlist();
                List<String> posterUrls = new ArrayList<>();
                for (String movieId : shortlist) {
                    try {
                        Movie movie = tmdbGateway.fetchDetails(movieId, null);
                        String posterPath = movie != null ? movie.getPosterPath() : null;
                        if (posterPath != null && !posterPath.isBlank()) {
                            posterUrls.add(tmdbGateway.buildPosterUrl(posterPath, "w300"));
                        } else {
                            posterUrls.add("");
                        }
                    } catch (Exception e) {
                        posterUrls.add(""); // fallback to empty on error
                    }
                }
                VoteState voteState = voteViewModel.getVoteState();
                voteState.setMovieIds(shortlist);
                voteState.setPosterUrls(posterUrls);
                voteState.setLocked(roomDataAccessObject.isLocked());
                voteState.setParticipantCount(roomDataAccessObject.participantsCount());

                // Get ballots and check if current user has voted
                List<Ballot> ballots = roomDataAccessObject.getBallots();
                voteState.setBallotsReceivedCount(ballots.size());
                String currentUsername = roomDataAccessObject.getUsername();
                boolean hasVoted = ballots.stream()
                        .anyMatch(b -> b.getParticipantId().equals(currentUsername));
                voteState.setHasVoted(hasVoted);
                // Set host flag for host-only actions (e.g., compute winner)
                voteState.setHost(roomDataAccessObject.isHost());

                // If a winner exists, set it and optionally navigate
                String winnerId = roomDataAccessObject.getWinnerMovieId();
                if (winnerId != null && !winnerId.isBlank()) {
                    voteState.setWinnerMovieId(winnerId);
                    if (viewManagerModel != null) {
                        viewManagerModel.setActiveViewName(ViewManagerModel.WINNER_VIEW);
                    }
                }

                voteViewModel.firePropertyChanged();
            }
        } catch (DataAccessException ex) {
            // No cooldown/debounce; surface error to shortlist presenter only
            shortlistPresenter.presentFailure(ex.getMessage());
        }
    }
}
