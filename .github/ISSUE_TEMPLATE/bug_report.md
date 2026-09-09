---
name: Bug Report
about: Report a bug or unexpected behavior in the Redmine Integration Service
title: "[BUG] <short description>"
labels: ["bug", "needs-triage"]
assignees: []
---

## Bug Description

<!-- A clear and concise description of what the bug is. -->

## Steps to Reproduce

1. Send request to endpoint `...`
2. With payload `...`
3. Observe response `...`

## Expected Behavior

<!-- What you expected to happen. -->

## Actual Behavior

<!-- What actually happened. Include the full error message or stack trace if available. -->

## Environment

| Property | Value |
|---|---|
| Service Version | e.g., `0.1.0` |
| Java Version | e.g., `OpenJDK 21` |
| Spring Boot Version | e.g., `3.x` |
| Redmine Version | e.g., `5.1.x` |
| Deployment | Docker / Kubernetes / Local |
| OS | e.g., Ubuntu 22.04 |

## API Request (if applicable)

```bash
# Paste the curl command or HTTP request here (redact API keys)
curl -X POST "http://localhost:8080/api/createUser?apiKey=REDACTED" \
  -H "Content-Type: application/json" \
  -d '{}'
```

## Response / Error Output

```
Paste the full response body or stack trace here
```

## Additional Context

<!-- Any other relevant information, logs, or screenshots. -->

---

> [!IMPORTANT]
> Do **not** include real API keys, passwords, or sensitive data in this report.
