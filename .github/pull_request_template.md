## Description

<!-- Provide a clear, concise description of what this PR changes and why. -->

Fixes #<!-- issue number (if applicable) -->

## Type of Change

- [ ] 🐛 Bug fix (non-breaking change that fixes an issue)
- [ ] ✨ New feature (non-breaking change that adds functionality)
- [ ] 💥 Breaking change (fix or feature that would cause existing functionality to change)
- [ ] 🔒 Security fix (addresses a vulnerability or hardens security)
- [ ] 📝 Documentation update
- [ ] ♻️ Refactor (no functional change)
- [ ] 🔧 Build / CI / config change

## Changes Made

<!-- List key changes made in this PR. Group by file or component. -->

- 
- 

## Testing

<!-- Describe how you tested this change. -->

- [ ] Ran `./mvnw clean test` — all tests pass
- [ ] Ran `./mvnw spotbugs:check` — no new security findings
- [ ] Ran `./mvnw dependency-check:check` — zero CVE findings
- [ ] Tested manually via Swagger UI at `http://localhost:8080/swagger-ui.html`
- [ ] Tested with Docker build: `docker build -t picc-pc-redmine-integration:test .`

## Checklist

- [ ] My code follows the [Development Guidelines](../DEVELOPMENT_GUIDELINES.md)
- [ ] I have added/updated Javadoc and OpenAPI annotations where applicable
- [ ] All log statements use `LogUtils.sanitizeForLog(...)` for external inputs (CWE-117)
- [ ] I have not included secrets, internal IPs, or credentials in this PR
- [ ] I have read and agree to the [Code of Conduct](../CODE_OF_CONDUCT.md)

## Screenshots / Logs (if applicable)

<!-- Add screenshots, curl outputs, or log snippets to demonstrate your change. -->
