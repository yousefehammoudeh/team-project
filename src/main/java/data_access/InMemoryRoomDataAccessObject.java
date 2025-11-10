package data_access;

import use_case.create_room.CreateRoomUserDataAccessInterface;
import use_case.join_room.JoinRoomUserDataAccessInterface;
import use_case.search.SearchUserDataAccessInterface;
import use_case.shortlist.ShortlistUserDataAccessInterface;
import use_case.vote.VoteUserDataAccessInterface;
import use_case.suggestions.SuggestionsUserDataAccessInterface;
import use_case.filters.FiltersUserDataAccessInterface;

/**
 * TODO: In-memory gateway for prototyping all room-related data access.
 *
 * Responsibilities:
 * - Store rooms, participants, shortlist, ballots, filters
 * - Provide search/suggestions gateway surface (stubbed/mocked)
 */
public abstract class InMemoryRoomDataAccessObject implements
        CreateRoomUserDataAccessInterface,
        JoinRoomUserDataAccessInterface,
        SearchUserDataAccessInterface,
        ShortlistUserDataAccessInterface,
        VoteUserDataAccessInterface,
        SuggestionsUserDataAccessInterface,
        FiltersUserDataAccessInterface {

    // TODO: Add in-memory maps/lists to back data

    // TODO: Implement interface methods with no-op or stubbed behavior for
    // scaffolding
}
