# RUMBO — Copilot Instructions

## Mandatory reading before any task

Before starting ANY task in this project, read these files in order:

1. **[docs/02-technical-spec.md](../docs/02-technical-spec.md)** — Architecture (Hexagonal), stack (Java 17 + Spring Boot 3.2 + Angular 17), Lombok/MapStruct/UUID conventions, testing strategy
2. **[docs/03-api-contract.md](../docs/03-api-contract.md)** — All endpoints, JWT auth, JSON response envelope format
3. **[docs/07-sdd-spec.md](../docs/07-sdd-spec.md)** — SDD workflow (MANDATORY before writing any code)

Do NOT generate code without having read `docs/07-sdd-spec.md` first.

---

## Project at a glance

**RUMBO** — Plataforma gamificada de educación financiera.

| Layer | Tech |
|-------|------|
| Backend | Java 17, Spring Boot 3.2, Hexagonal Architecture (Ports & Adapters) |
| Frontend | Angular 17, TypeScript 5.2 strict, Standalone components |
| Database | PostgreSQL 15 (Docker), Liquibase YAML migrations |
| Auth | JWT stateless, Spring Security |
| IDs | UUID everywhere — NEVER Long/Integer/autoincrement |
| Build | Maven (backend), npm (frontend) |
| Docker | `rumbo_postgres` container (NOT `rumbo_db`) |

---

## Non-negotiable conventions

- **Lombok** on every Java class — `@Data @Builder @NoArgsConstructor @AllArgsConstructor` (models/entities), `@RequiredArgsConstructor` (services/controllers), `@Slf4j` (logging). NEVER write getters/setters/constructors by hand.
- **MapStruct** `@Mapper(componentModel = "spring")` for ALL inter-layer conversions. NEVER `BeanUtils.copyProperties` or manual constructors between layers.
- **Constructor injection only** — never `@Autowired` on fields.
- **UUID** for all entity IDs — `@GeneratedValue(strategy = GenerationType.UUID)`.
- **No git operations** — never `git commit`, `git push`, `git reset`. The human controls all git actions.
- **No build after changes** — never run `mvn package`, `mvn install`, or `ng build`. Let the user decide.
- **SDD mandatory** — no code without User Story → Spec → Design → approval. See `docs/07-sdd-spec.md`.

---

## Hexagonal layer rules (STRICT)

```
com.rumbo/
├── domain/model/               # Pure Java POJOs — ZERO Spring, ZERO JPA annotations
├── application/port/in/        # Use case interfaces (inbound ports)
├── application/port/out/       # Repository interfaces (outbound ports)
├── application/service/        # @Service — implements inbound ports
├── infrastructure/adapter/in/web/          # @RestController — calls inbound ports
├── infrastructure/adapter/out/persistence/ # @Component — implements outbound ports (JPA)
└── infrastructure/config/      # @Configuration — Spring wiring, Security, JWT
```

**Dependency rule**: domain ← application ← infrastructure. NEVER reversed.
`@Transactional` only in application services. DTOs live in web adapter package, NOT in domain.

---

## SDD workflow (summary)

Every feature follows this pipeline — no exceptions:

```
US (functional/US/) → Explore → Propose → Spec → Design → [approval] → Tasks → Apply → Verify → Archive
```

- No User Story = no code. A chat description is NOT a User Story.
- Design phase requires explicit user approval before implementation starts.
- Each acceptance criterion (AC-XX) from the US maps to exactly one spec scenario.

---

## All documentation

| Doc | Purpose |
|-----|---------|
| [docs/01-product-spec.md](../docs/01-product-spec.md) | Product vision, all 11 User Stories, success metrics |
| [docs/02-technical-spec.md](../docs/02-technical-spec.md) | Architecture, stack, conventions (Lombok/MapStruct/UUID), testing |
| [docs/03-api-contract.md](../docs/03-api-contract.md) | Endpoints, JWT auth, error codes, response envelope |
| [docs/04-frontend-spec.md](../docs/04-frontend-spec.md) | Angular structure, components, design system, state management |
| [docs/05-test-plan.md](../docs/05-test-plan.md) | Testing strategy per layer, coverage requirements |
| [docs/06-ai-usage-log.md](../docs/06-ai-usage-log.md) | AI session log — update after every session |
| [docs/07-sdd-spec.md](../docs/07-sdd-spec.md) | Full SDD rules — read before ANY code generation |
| [functional/US/](../functional/US/) | All User Stories (US-001 to US-011) |
| [AGENTS.md](../AGENTS.md) | Agent roles, responsibilities, common tasks |
