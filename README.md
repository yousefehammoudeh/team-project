# Group Movie Night Voting System

> A JavaGUI app that helps friends pick a movie together: search titles, build a shortlist, vote with rankings, and announce a winner. Built with Clean Architecture and SOLID principles.

---

## 🧭 Summary

The app lets a host create a **Room** with a shareable code. Friends join the room, **search** movies (TMDB), **add** to a shortlist, then **vote** using ranked ballots (e.g., Borda scoring). The host locks the list and publishes the winner.  
Key goals:

---

## ✅ User Stories & Owners

> Keep this table up-to-date. One story per member early in the term is recommended. (You can add more later.)

| ID  | User Story (acceptance summary)                                                                                                    | Owner  | Status | PR(s) |
|-----|------------------------------------------------------------------------------------------------------------------------------------|--------|---|-------|
| S01 | **Create Room (Host):** generate room code + host token; persist room; show host dashboard.                                        | Diana  | ☐ |       |
| S02 | **Join Room (Participant):** enter code + name; join and see current state.                                                        | Elaine | ☐ |       |
| S03 | **Search & Details:** search TMDB; show list (title/year/poster); open details panel.                                              | Tamako | ☐ |       |
| S04 | **Build & Lock Shortlist (Host):** add/remove until lock; lock requires host token.                                                | He Sun | ☐ |       |
| S05 | **Vote & Winner:** participants submit ranked ballots; host computes and displays winner.                                          | Yousef | ☐ |       |
| S06 | **Suggest Movie:** once a movie is selected and/or marked as watched, it will recommend sequels or other movies of similar genres. | TBA    | ☐ |       |
| S07 | **Content Filters (Host):** apply content filters (e.g., exclude adult content, set minimum rating, language)                      | TBA    | ☐ |       |

---

## 🔌 APIs Used

### The Movie Database (TMDB)
- **What we use:** Movie search, details, poster paths
- **Auth:** **V4 Read Access Token** (Bearer) stored outside the repo (e.g., env var `TMDB_BEARER_V4`)
- **Typical calls:**
    - `GET /search/movie?query=...&include_adult=false&language=en-US&page=1`
    - `GET /movie/{id}?language=en-US`
---

## 🧪 Screenshots & Demos

> Add screenshots or short GIFs here as functionality lands. Keeping this section current makes the final presentation easy. :contentReference[oaicite:9]{index=9}

- `docs/screenshots/home.png` – Home (Create vs Join)
- `docs/screenshots/search.png` – Search results + details
- `docs/screenshots/shortlist.png` – Host shortlist + lock
- `docs/screenshots/vote.gif` – Voting flow + winner

---
## 🗂️ Chronological Backlog (Issues + Sub‑issues)

Create the following GitHub issues in order. Each issue lists sub‑issues (todos). Difficulty: [Easy], [Medium], [Challenging].

1) Project Build & Repo Setup [Easy]
- [x] Add run targets (local) and CI stub (GitHub Actions)
- [x] Document env vars (TMDB token) and setup in README

2) Core Entities Defined [Easy]
- [ ] Room fields + accessors (code, hostToken, participants, shortlist, locked, selectedMovieId, contentFilters)
- [ ] Participant fields + accessors (id, name)
- [ ] Movie fields + accessors (id, title, year, posterPath, genres, language, rating)
- [ ] Ballot fields + accessors (participantId, rankedMovieIds)
- [ ] Shortlist fields + accessors (movieIds, locked)
- [ ] ContentFilters fields + accessors (excludeAdult, minRating, language, genre includes/excludes)

3) View Infrastructure & Switching [Easy]
- [ ] Finalize `interface_adapter/ViewModel` helpers and defaults
- [ ] Add view name constants + active view to `ViewManagerModel`
- [ ] Implement basic `view/ViewManager` switching (CardLayout or equivalent)

4) UI Skeletons Created [Easy]
- [ ] JoinRoomView: inputs (code, name) and placeholder labels
- [ ] SearchView: search field, results list, details panel placeholder
- [ ] ShortlistView: list + add/remove buttons
- [ ] VoteView: rankable list + submit
- [ ] FiltersView: adult toggle, min rating, language
- [ ] SuggestionsView: list placeholder
- [ ] HostDashboardView: room code, host token, action buttons (lock, winner, filters)

5) In‑Memory DAO Scaffolding [Easy]
- [ ] Declare maps/lists to hold rooms, participants, ballots, filters in `data_access/InMemoryRoomDataAccessObject`
- [ ] Stub methods (no logic) to satisfy interfaces

6) Use Case Contracts (All Stories) [Medium]
- [ ] Define InputBoundary method signatures for S01–S07
- [ ] Define InputData/OutputData fields per story (roomCode, hostToken, participantId, query, filters, etc.)
- [ ] Expand `*UserDataAccessInterface` signatures to support the above

7) App Composition Wiring [Medium]
- [ ] `app/AppBuilder`: instantiate DAO, presenters, interactors, controllers, view models, and views per story
- [ ] Register views with `ViewManager`; set initial active view
- [ ] `app/Main`: launch on EDT and call builder

8) Presenter/State Plumbing [Medium]
- [ ] Add `error` and minimal state fields to all `*State` classes
- [ ] Implement `present(...)` and `presentFailure(...)` to update state and call `firePropertyChanged()`
- [ ] Hook view buttons/inputs to controllers (handlers only; still no interactor logic)

9) S01 Create Room — Implementation + Tests [Challenging]
- [ ] Generate unique room code + host token
- [ ] Persist room via DAO
- [ ] Presenter maps to dashboard state
- [ ] HostDashboardView displays code/token
- [ ] Unit tests for interactor behavior

10) S02 Join Room — Implementation + Tests [Challenging]
- [ ] Validate room code and participant name
- [ ] Add participant to room via DAO
- [ ] Presenter returns room summary
- [ ] JoinRoomView updates and navigates to room context
- [ ] Unit tests for interactor behavior

11) S04 Shortlist — Implementation + Tests [Challenging]
- [ ] Add candidate movie to shortlist
- [ ] Remove candidate movie from shortlist
- [ ] Lock shortlist (host token required)
- [ ] Update ShortlistView state
- [ ] Unit tests including lock invariants

12) S03 Search & Details — Stub, Then TMDB Gateway [Challenging]
- [ ] Implement stubbed search results in DAO for early UI integration
- [ ] Wire SearchView to trigger interactor and update results
- [ ] Implement TMDB gateway (env token) behind `SearchUserDataAccessInterface`
- [ ] Map details calls and update details panel
- [ ] Unit tests with test doubles

13) S05 Vote & Winner — Implementation + Tests [Challenging]
- [ ] Submit ranked ballots and persist
- [ ] Compute winner (Borda baseline) with tie policy
- [ ] Presenter updates winner state; View shows winner
- [ ] Unit tests for scoring and edge cases

14) S07 Content Filters — End‑to‑End + Integration [Challenging]
- [ ] Save filters in DAO (host token validation)
- [ ] Apply filters in search gateway
- [ ] Update FiltersView; ensure Search respects filters
- [ ] Unit/integration tests

15) S06 Suggestions — Implementation + Tests [Challenging]
- [ ] Suggest sequels and similar‑by‑genre
- [ ] Merge/score suggestions; update view
- [ ] Unit tests with gateway doubles

16) Polish, UX, and Integration Plan [Medium]
- [ ] Error messages across views and presenters
- [ ] Ensure EDT updates on UI changes; add simple loading states
- [ ] Add screenshots/GIFs and usage notes to README
- [ ] Outline manual/integration test plan and CI hooks
