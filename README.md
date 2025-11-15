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
| S08 | **Manage Participants (Host):** host can check on participants and remove participants.                                            | TBA    | ☐ |       |

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
 - [x] Room fields + accessors (code, hostToken, participants, shortlist, locked, selectedMovieId, contentFilters) — `entity/Room.java` present
 - [x] Participant fields + accessors (id, name) — `entity/Participant.java` present
 - [x] Movie fields + accessors (id, title, year, posterPath, genres, language, rating) — `entity/Movie.java` present
 - [x] Ballot fields + accessors (participantId, rankedMovieIds) — `entity/Ballot.java` present
 - [x] Shortlist fields + accessors (movieIds, locked) — `entity/Shortlist.java` present
 - [x] ContentFilters fields + accessors (excludeAdult, minRating, language, genre includes/excludes) — `entity/ContentFilters.java` present

3) View Infrastructure & Switching [Easy]
 - [x] `interface_adapter/ViewModel` and `ViewManagerModel` present (`interface_adapter/ViewModel.java`, `interface_adapter/ViewManagerModel.java`)
 - [x] `view/ViewManager` and basic view classes present (`view/ViewManager.java`, various `view/*.java`)
 - [ ] Final polish: finalize helpers, constants and ensure smooth switching logic (in-progress)

4) UI Skeletons Created [Easy]
- [x] JoinRoomView: inputs (code, name) and placeholder labels — `view/JoinRoomView.java`
- [x] SearchView: search field, results list, details panel placeholder — `view/SearchView.java`
- [x] ShortlistView: list + add/remove buttons — `view/ShortlistView.java`
- [x] VoteView: rankable list + submit — `view/VoteView.java`
- [x] FiltersView: adult toggle, min rating, language — `view/FiltersView.java`
- [x] SuggestionsView: list placeholder — `view/SuggestionsView.java`
- [x] HostDashboardView: room code, host token, action buttons (lock, winner, filters) — `view/HostDashboardView.java`

Note: these are UI skeletons (views and view models/presenters exist). Wiring and behavior are in progress in many places.

5) In‑Memory DAO Scaffolding [Easy]
- [x] In‑memory DAO file present: `data_access/InMemoryRoomDataAccessObject.java` (maps/lists declared)
- [ ] Implement method logic and additional persistence helpers (in-progress)

6) Use Case Contracts (All Stories) [Medium]
- [x] Many use-case interfaces and interactor classes present under `use_case/` (create_room, add_movie, search, shortlist, suggestions, vote)
- [ ] Complete and stabilize all InputBoundary/InputData/OutputData contracts for every story (in-progress)

7) App Composition Wiring [Medium]
- [x] `app/AppBuilder.java` and `app/Main.java` exist and provide composition entry points
- [ ] Final wiring, dependency injection and startup ordering need validation (in-progress)

8) Presenter/State Plumbing [Medium]
- [x] Presenter and State classes exist for most features under `interface_adapter/*/` (e.g., `CreateRoomPresenter`, `ShortlistPresenter`, `SearchPresenter`, `VotePresenter`, etc.)
- [ ] Finish consistent `present`/`presentFailure` implementations and event firing (in-progress)

9) S01 Create Room — Implementation + Tests [Challenging]
 - [ ] S01 Create Room — implementation in progress (use_case + presenter/controller/view files present; add interactor tests)

10) S02 Join Room — Implementation + Tests [Challenging]
 - [ ] S02 Join Room — implementation in progress (interfaces and controllers present)

11) S04 Shortlist — Implementation + Tests [Challenging]
 - [ ] S04 Shortlist — implementation in progress (shortlist presenter and controllers present)

12) S03 Search & Details — Stub, Then TMDB Gateway [Challenging]
 - [ ] S03 Search & Details — TMDB gateway class present (`data_access/tmdb/TmdbMovieGateway.java`); wiring and tests remain to be completed

13) S05 Vote & Winner — Implementation + Tests [Challenging]
 - [ ] S05 Vote & Winner — controllers and presenter classes exist; winner computation and tests pending

14) S07 Content Filters — End‑to‑End + Integration [Challenging]
 - [ ] S07 Content Filters — `filters` presenter and view classes exist; persistence and integration tests pending

15) S06 Suggestions — Implementation + Tests [Challenging]
 - [ ] S06 Suggestions — suggestion presenter and view exist; gateway scoring and tests pending

16) Polish, UX, and Integration Plan [Medium]
- [ ] Polish, UX, and Integration — ongoing; some unit tests and UI tests exist under `test/` (shortlist, host dashboard, participants dashboard)

