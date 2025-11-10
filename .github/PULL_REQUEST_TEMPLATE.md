## PR title format

- Required: Prefix every PR title with one of the story codes followed by a hyphen and a concise kebab-case summary of the functionality.
- Valid story codes:
  - `S00` — general feature / cross-cutting work
  - `S01` — Create Room (Host)
  - `S02` — Join Room (Participant)
  - `S03` — Search & Details
  - `S04` — Build & Lock Shortlist (Host)
  - `S05` — Vote & Winner
  - `S06` — Suggest Movie
  - `S07` — Content Filters (Host)

Example title: `S02-add-user-login` or `S00-update-dependencies`

## Labels

- Please add a label that matches the story code (e.g. `S02`) and, if helpful, an implementation label in kebab-case (e.g. `add-user-login`).
- At minimum: add exactly one story label from `S00`..`S07`.

Note: this repository requires PRs to be labeled with one of `S00`..`S07` so reviewers and automation can categorize work. If you do not have permission to add labels, add a comment requesting the appropriate label(s) from a maintainer.

## Description

Provide a short, focused summary of what changed and why. Include the important technical details a reviewer needs to understand the change quickly.

- Why: Short rationale or user story this implements.
- How: High-level notes about approach and any non-obvious implementation details (e.g., new packages, architecture notes, important tradeoffs).

## Issues / Tickets

- Closes: #<issue-number> (if relevant)
- Related: #<issue-number> (if relevant)

If no issue exists, briefly state which user story or acceptance criteria this PR addresses.

## Checklist (required)

- [ ] PR title uses required format (e.g. `S02-add-user-login`) and story label added (`S02`)
- [ ] The PR is labeled with one of `S00`..`S07`
- [ ] The kebab-case functionality label or descriptive tag is included (recommended)
- [ ] Description contains what/why/how
- [ ] Linked issues are referenced
- [ ] Tests added or existing tests updated (if applicable)
- [ ] Build is successful locally
- [ ] No sensitive information included

--
If you are unsure which story code to use, pick `S00` and note the intended story code in the description so maintainers can relabel.

Thank you for keeping PRs consistent — it makes reviewing much easier.
