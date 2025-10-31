# Project Blueprint — ReelRound (Group Movie Night Voting System)

**Team Name:** Group 23  
**Domain:** Collaborative Entertainment Planning

A desktop application that helps a group of friends **pick a movie together**.  
A host creates a room and shares a code; participants join, **search titles from TMDB**, build a **short-list**, and **vote** using ranked ballots.  
The host locks the list and announces the **winner**. Optional features include content filters, provider info (where to watch), and session history.

---

## User Stories

1. **Create Room (Host)** — As a host, I can create a room that generates a **shareable code** and a **private host token**, so I can manage the session.
2. **Join Room (Participant)** — As a participant, I can join with a code and a display name, so I can view the short-list and vote.
3. **Search & Details (TMDB)** — As any user in a room, I can search movies and view **title/year/poster/overview**, so I can propose candidates.
4. **Build & Lock Short-List (Host)** — As a host, I can add/remove titles and then **lock** the short-list, so voting can begin.
5. **Vote & Winner** — As a participant, I can submit a **ranked ballot**; as a host, I can compute and display the **winner**.
6. **Suggest Movie (Post-Winner)** — As a user, after a movie is selected or marked watched, I get **sequels/similar** recommendations for next time.
7. **Content Filters (Host)** — As a host, I can apply **filters** (exclude adult content, minimum rating, language), so results match the group’s needs.

---

## Use Cases

### **Use Case 1: Create Room**
**Main Flow**
- Host clicks **Create Room**.
- System generates a 6-character `roomCode` and a secret `hostToken` (stored hashed).
- System persists an empty room (`status=COLLECTING`, empty short-list, no ballots).

**Alternative Flows**
- Persistence failure → retry.
- Code collision → regenerate code.

---

### **Use Case 2: Join Room**
**Main Flow**
- Participant enters `roomCode` and `displayName`.
- System validates the code, creates a `participantId`, and shows room state.

**Alternative Flows**
- Invalid code → error message.
- Empty name → validation error.

---

### **Use Case 3: Search Movies (TMDB)**
**Main Flow**
- User enters a query and hits **Search**.
- System calls TMDB Search API and maps results to domain `Film` (id, title, year, poster).

**Alternative Flows**
- Empty query → no call.
- Network/API error → display error message.

---

### **Use Case 4: Manage Short-List (Host)**
**Main Flow**
- Host adds titles from search results to the short-list (no duplicates).
- Host can remove items until locked.

**Alternative Flows**
- Non-host tries to modify → denied.

---

### **Use Case 5: Lock & Vote (Host + Participants)**
**Main Flow**
- Host provides `hostToken` to **Lock Short-List** (`status=VOTING`).
- Participants submit **ranked ballots** (e.g., Borda scoring).
- Host triggers **Compute Winner**; system aggregates ballots and displays winner and ranking.

**Alternative Flows**
- Duplicate ballot by same participant → replace previous.
- Empty ballot → validation error.

---

### **Use Case 6: Suggest Movie (Recommendations)**
**Main Flow**
- After winner is chosen, user opens **Suggestions**.
- System calls TMDB “similar” or “recommendations” endpoints to display options.

**Alternative Flows**
- No suggestions → show new search prompt.

---

### **Use Case 7: Apply Content Filters (Host)**
**Main Flow**
- Host opens **Filters** panel before or during movie search.
- Host sets preferences (exclude adult content, min rating, language).
- System applies filters to all TMDB queries.

**Alternative Flows**
- Invalid filter input → show error or reset to default.

---

## MVP Table

> *The MVP is now sized for a 5-person team, with each developer owning one core use case.*

| Use Case                       | User Story | Lead Developer   |
| ------------------------------ | ---------- |------------------|
| Create Room                    | Story 1    | Yousef Hammoudeh |
| Join Room                      | Story 2    | Tamako Fan       |
| Search Movies                  | Story 3    | Elaine Xing      |
| Manage Short-List (add/remove) | Story 4    | Diana Luo        |
| Vote & Winner                  | Story 5    | He Sun           |

**Post-MVP (next milestone):**
- Suggest Movie (Story 6)
- Content Filters (Story 7)

---

## Proposed Entities

| Entity | Attributes |
|---------|-------------|
| **Room** | `code`, `hostTokenHash`, `status ∈ {COLLECTING, VOTING, CLOSED}`, `shortlist: List<Film>`, `ballots: Map<participantId, Ballot>`, `createdAt`, `updatedAt` |
| **Film** | `tmdbId`, `title`, `year`, `posterPath`, `overview`, `genres` |
| **Participant** | `participantId`, `displayName` |
| **Ballot** | `rankedTmdbIds: List<Integer>` (ranking order) |
| **Filters** *(optional)* | `excludeAdult: boolean`, `minRating: int`, `language: String` |

- Room --> Film : uses
- Room --> Ballot : uses
- Room --> Filters : uses
- Participant --> Ballot : uses
---

## Proposed API

**API Name:** The Movie Database (TMDB)  
**Base URL:** https://api.themoviedb.org/3

**Endpoints Used:**
- `/search/movie` — Search movies by title
- `/movie/{id}` — Get movie details
- `/movie/{id}/recommendations` — Get recommendations
- `/movie/{id}/similar` — Get similar movies
- *(Optional)* `/movie/{id}/watch/providers` — Fetch streaming availability

**Auth:** V4 Read Access Token (Bearer, stored in environment variable).  
**Notes:** Validate endpoints using Hoppscotch or Postman; cache posters locally for performance.

**Optional Persistence:**  
Use the **Course Grade API** (JSON blobs keyed by `roomCode`) for early milestones before migrating to a database.

---

## Ownership Suggestions

For a 5-member team:
- Each member owns one core story (S01–S05).
- Later, pair on advanced features (filters, recommendations).
- Keep PRs small — one use case, interactor tests, and minimal UI per PR.

---
