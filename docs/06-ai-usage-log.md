# AI Usage Log

> This document tracks AI agent participation in the RUMBO project.
> All sessions must comply with the rules defined in [07-sdd-spec.md](07-sdd-spec.md):
> **no code is generated without a prior specification, design, and explicit approval.**

---

## Mandatory Pre-Code Checklist (07-sdd-spec.md)

Before any code generation, the agent MUST confirm:

- [ ] **Specification exists**: business intent, functional requirements, acceptance criteria, non-goals
- [ ] **Design proposed**: hexagonal layer mapping (domain / application ports / infrastructure adapter) reviewed and approved
- [ ] **Tasks decomposed**: ordered from domain model → ports → services → adapters → config → tests
- [ ] **Approval received**: user confirmed design and task list before implementation started

If any item is unchecked, the agent MUST stop and request the missing artifact.

---

## Technology Reference (02-technical-spec.md)

All backend code produced in any session MUST conform to:

| Concern | Requirement |
|---------|-------------|
| Language | Java 21 |
| Framework | Spring Boot 3.2.x |
| Architecture | Hexagonal (Ports & Adapters) — see package structure in 02-technical-spec.md |
| Persistence | Spring Data JPA + Hibernate 6. No domain class may have JPA annotations. |
| Validation | Bean Validation (`@Valid`, `@NotNull`, etc.) on DTOs only — never on domain objects |
| Security | Spring Security + JWT. `@PreAuthorize` / `@Secured` in web adapters only. |
| Testing | JUnit 5 + Mockito + AssertJ. `@SpringBootTest` only when full context is required. |
| Build | Maven 3.9+ via `./mvnw`. Coverage enforced by JaCoCo (`./mvnw verify`). |
| Injection | Constructor injection only — never `@Autowired` on fields. |

All frontend code MUST conform to:

| Concern | Requirement |
|---------|-------------|
| Framework | Angular 17, standalone components (`standalone: true`) |
| Language | TypeScript 5.2 strict mode |
| HTTP | `HttpClientModule` + `AuthInterceptor` (JWT Bearer). No direct `HttpClient` calls in components. |
| State | `BehaviorSubject` / `signal()` in services. `async` pipe in templates. |
| Testing | Karma + Jasmine. `HttpClientTestingModule` always. No real HTTP in tests. |
| Coverage | 70% minimum on services and page components. |

---

## Session Log

### Session 2026-05-04 — Architecture Migration & Docs Alignment

**Participants**: Copilot (Setup Agent)

**SDD compliance**:
- No new feature code was generated in this session
- Sessions consisted of spec and documentation updates only — SDD pre-code checklist N/A

**Tasks Completed**:
- [x] Created initial project structure
- [x] Set up Java 21 + Spring Boot 3.2 backend scaffold
- [x] Set up Angular 17 frontend with standalone components
- [x] Created Docker Compose configuration
- [x] Migrated `02-technical-spec.md` from Python/Flask to Java 21 + Spring Boot + Hexagonal Architecture
- [x] Migrated `04-frontend-spec.md` to Angular 17 aligned with updated backend spec
- [x] Migrated `05-test-plan.md` to JUnit 5 + Mockito (backend) / Karma+Jasmine (frontend)
- [x] Created `07-sdd-spec.md` — Spec-Driven Development skill for Java + Spring + Hexagonal
- [x] Started Java runtime upgrade: Java 17 → Java 21 (plan confirmed, execution in progress)

**Decisions Made**:
- Backend: **Java 21 + Spring Boot 3.2** (migrated from Python/Flask)
- Architecture: **Hexagonal (Ports & Adapters)** — domain has zero framework dependencies
- Database: PostgreSQL (production), H2 (tests)
- Testing: JUnit 5 + Mockito + AssertJ + JaCoCo (backend), Karma + Jasmine (frontend)
- Coding workflow: **Spec-Driven Development** enforced via `07-sdd-spec.md` — specification and design required before any code

**Files Created / Updated**:
- `docs/02-technical-spec.md` — Migrated to Java + Spring Boot + Hexagonal package structure
- `docs/04-frontend-spec.md` — Aligned with updated backend, added interceptors/guards/models structure
- `docs/05-test-plan.md` — Full rewrite: layer matrix, JUnit 5 commands, JaCoCo gates
- `docs/07-sdd-spec.md` — New SDD skill: Java + Spring + Hexagonal layer mapping + testing rules
- `backend/pom.xml` — Upgrade in progress: `<java.version>17</java.version>` → `21`

**Next Steps**:
- Complete Java 21 upgrade (Steps 2–4 of upgrade plan `20260504094254`)
- Implement authentication system (Login / Register) following SDD workflow
- Create first domain entity and use case following hexagonal package structure
- Set up CI/CD pipeline

---

### Session Template — [Date] - [Description]

**Participants**: [Agent names]

**SDD compliance**:
- [ ] Specification reviewed before coding started
- [ ] Design (hexagonal layer mapping) proposed and approved
- [ ] Tasks decomposed and ordered
- [ ] Code reviewed against acceptance criteria post-implementation

**Tasks Completed**:
- [ ] Task 1 (link to relevant spec section)
- [ ] Task 2

**Decisions Made**:
- Decision with rationale

**Files Created / Updated**:
- `path/to/file` — what changed and why

**Next Steps**:
- Pending work for next session

---

Last updated: 2026-05-04
