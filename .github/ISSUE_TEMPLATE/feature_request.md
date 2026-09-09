---
name: Feature Request
about: Propose a new endpoint, integration, or improvement
title: "[FEAT] <short description>"
labels: ["enhancement", "needs-triage"]
assignees: []
---

## Summary

<!-- A clear and concise description of the feature you are requesting. -->

## Motivation / Problem Statement

<!-- What problem does this solve? Why is this feature needed? 
     Example: "As a platform service, I need to query Redmine memberships by user ID rather than project ID." -->

## Proposed Solution

<!-- Describe the solution you'd like. Include any API endpoint design, request/response format, or behavior. -->

### Example API Design (if applicable)

```
GET /api/getUserMemberships?userId=42&apiKey=...

Response 200:
[
  { "project_id": 5, "role_id": 4 },
  { "project_id": 12, "role_id": 3 }
]
```

## Alternatives Considered

<!-- Have you considered any alternative solutions or workarounds? -->

## Additional Context

<!-- Links to Redmine API documentation, related issues, or any other relevant context. -->

## Checklist

- [ ] I have searched existing issues to confirm this is not a duplicate.
- [ ] This feature aligns with the Redmine Integration Service scope (user, project, membership, issue management).
