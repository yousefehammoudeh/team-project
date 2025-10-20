# Group Movie Night Voting System

> A JavaGUI app that helps friends pick a movie together: search titles, build a shortlist, vote with rankings, and announce a winner. Built with Clean Architecture and SOLID principles.

---

## 🧭 Summary

The app lets a host create a **Room** with a shareable code. Friends join the room, **search** movies (TMDB), **add** to a shortlist, then **vote** using ranked ballots (e.g., Borda scoring). The host locks the list and publishes the winner.  
Key goals:

---

## ✅ User Stories & Owners

> Keep this table up-to-date. One story per member early in the term is recommended. (You can add more later.)

| ID  | User Story (acceptance summary)                                                                                                    | Owner | Status | PR(s) |
|-----|------------------------------------------------------------------------------------------------------------------------------------|-------|---|-------|
| S01 | **Create Room (Host):** generate room code + host token; persist room; show host dashboard.                                        | TBD   | ☐ |       |
| S02 | **Join Room (Participant):** enter code + name; join and see current state.                                                        | TBD   | ☐ |       |
| S03 | **Search & Details:** search TMDB; show list (title/year/poster); open details panel.                                              | TBD   | ☐ |       |
| S04 | **Build & Lock Shortlist (Host):** add/remove until lock; lock requires host token.                                                | TBD   | ☐ |       |
| S05 | **Vote & Winner:** participants submit ranked ballots; host computes and displays winner.                                          | TBD   | ☐ |       |
| S06 | **Suggest Movie:** once a movie is selected and/or marked as watched, it will recommend sequels or other movies of similar genres. | TBA   | ☐ |       |
| S07 | **Content Filters (Host):** apply content filters (e.g., exclude adult content, set minimum rating, language)                      | TBA   | ☐ |       |
| S08 | *Add your own user story*                                                                                                          | TBA   | ☐ |       |
| S09 | *Add your own user story*                                                                                                          | TBA   | ☐ |       |
| S10 | *Add your own user story*                                                                                                          | TBA   | ☐ |       |

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
