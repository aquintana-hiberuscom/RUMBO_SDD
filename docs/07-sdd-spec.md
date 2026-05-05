---
name: spec-driven-development
description: >
  Enforce Spec Driven Development for Java + Spring Boot + Hexagonal Architecture.
  Guide Copilot to work using explicit specifications as the source of truth before
  generating code. Keywords: spec-driven development, SDD, specification, hexagonal
  architecture, ports and adapters, Spring Boot, Java, design-first.
---

# Spec Driven Development Skill — Java + Spring Boot + Hexagonal Architecture

You are operating under **Spec Driven Development (SDD)** rules.

Your goal is to produce **clean, maintainable, and intentional code** by
treating the specification as the **primary source of truth**.

The architecture is **Hexagonal (Ports & Adapters)**. Every decision must
respect the dependency rule: the **domain** has zero dependencies on infrastructure.

You MUST follow the workflow below strictly.

---

## 0. User Story First (MANDATORY)

Before any specification, there MUST be a User Story.

A User Story is the origin of every feature. It defines **who** needs something, **what** they need, and **why** — in business terms, not technical terms.

**Format** (strict — no variations):
```
As a {role},
I want {goal},
so that {benefit}.
```

User Stories live in `functional/US/` following the naming convention `US-{NNN}-{slug}.md`.
Use the `us-writer` skill to create them.

### A valid US MUST pass INVEST:

| Criterion | Rule |
|-----------|------|
| **Independent** | Can be built without another incomplete story |
| **Negotiable** | Scope can still be discussed — not a fixed contract |
| **Valuable** | Delivers value to a real user role |
| **Estimable** | Team can assign story points to it |
| **Small** | Fits within one sprint |
| **Testable** | Every acceptance criterion can be verified |

### US → SDD traceability

Every US has acceptance criteria (Given/When/Then). These criteria become the scenarios in the spec phase, not new inventions:

```
US AC-01 (happy path)  →  Spec Scenario: {title}
US AC-02 (edge case)   →  Spec Scenario: {title}
US AC-03 (error case)  →  Spec Scenario: {title}
```

**If a request has no User Story:**
- STOP
- Ask the user to write one using the `us-writer` skill
- DO NOT accept a feature description as a substitute for a US

---

## 1. Specification First (MANDATORY)

Before writing any code, ensure there is a clear specification tied to a User Story.

A valid specification MUST include:
- **Source US**: reference to `functional/US/US-{NNN}-{slug}.md`
- **Business intent**: What problem does this solve and for whom?
- **Functional requirements**: Use cases, commands, queries, events
- **Non-functional requirements**: Performance, security, transactional boundaries, constraints
- **Acceptance criteria**: Verifiable statements in Given/When/Then or equivalent form
- **Assumptions and explicit non-goals**: What is intentionally out of scope?

If the request does NOT include a specification:
- STOP
- Ask the user to provide or confirm the specification
- DO NOT generate code

---

## 2. Design Before Implementation

Once the specification is clear, map it to the hexagonal layers:

### Layer mapping (MANDATORY)

| Layer | Package | Spring role | Contains |
|-------|---------|-------------|----------|
| **Domain** | `domain/model` | None (POJOs) | Entities, Value Objects, Domain Events, Aggregates |
| **Application** | `application/port/in` | None (interfaces) | Inbound Ports (use case interfaces) |
| **Application** | `application/port/out` | None (interfaces) | Outbound Ports (repository, messaging interfaces) |
| **Application** | `application/service` | `@Service` | Use Case implementations (orchestrate domain) |
| **Infrastructure** | `infrastructure/adapter/in/web` | `@RestController` | HTTP Adapters (call inbound ports) |
| **Infrastructure** | `infrastructure/adapter/out/persistence` | `@Repository` | Persistence Adapters (implement outbound ports) |
| **Infrastructure** | `infrastructure/adapter/out/messaging` | `@Component` | Messaging Adapters |
| **Infrastructure** | `infrastructure/config` | `@Configuration` | Spring wiring, beans |

For each design decision, explicitly state:
- Which **inbound port** (use case interface) is being created or used
- Which **outbound port** (repository/service interface) is needed
- What the **domain model** (entities, value objects) looks like
- Where **Spring annotations** appear — ONLY in infrastructure and application service layers

Do NOT implement yet. Wait for explicit approval.

---

## 3. Task Decomposition

Break the design into **small, ordered, atomic tasks** following the dependency direction:

1. Domain model first (entities, value objects) — no Spring, no JPA
2. Outbound ports (interfaces in `application/port/out`)
3. Inbound ports (use case interfaces in `application/port/in`)
4. Application services (implement inbound ports, depend on outbound ports)
5. Persistence adapters (implement outbound ports, use Spring Data JPA)
6. Web adapters (REST controllers implementing or calling inbound ports)
7. Spring configuration / wiring
8. Tests (see Testing Rules below)

Each task must be:
- Independently compilable and reviewable
- Accompanied by its test (unit or integration)
- Free of circular dependencies between layers

---

## 4. Implementation Rules

Only after explicit approval or continuation of the specification and design.

### General rules
- Follow clean code principles: readability over cleverness
- Keep classes and methods small and intention-revealing
- No unnecessary abstractions — only introduce patterns justified by the spec
- Respect existing naming, layering, and package structure

### Hexagonal rules (STRICT)
- **Domain classes** have NO Spring annotations, NO JPA annotations, NO framework imports
- **Application services** depend ONLY on port interfaces, never on concrete infrastructure classes
- **Inbound ports** are interfaces; controllers call them, they do NOT call controllers
- **Outbound ports** are interfaces; application services call them, infrastructure implements them
- **No domain logic** in controllers, adapters, or Spring configuration classes
- **No `@Autowired` on fields** — use constructor injection only

### Spring Boot rules
- Use `@RestController` + `@RequestMapping` in web adapters only
- Use `@Transactional` only in application services, never in domain or adapters
- Use `@SpringBootTest` sparingly — prefer unit tests for domain and application layers
- DTOs (request/response) live in the web adapter package, NOT in the domain

---

## 5. Testing Rules

| Layer | Test type | Tool | Annotation |
|-------|-----------|------|------------|
| Domain | Unit test | JUnit 5, AssertJ | None |
| Application service | Unit test | JUnit 5, Mockito | `@ExtendWith(MockitoExtension.class)` |
| Persistence adapter | Integration test | Spring Boot Test + H2/Testcontainers | `@DataJpaTest` |
| Web adapter | Slice test | MockMvc | `@WebMvcTest` |
| Full flow | Integration test | Spring Boot Test | `@SpringBootTest` |

- Domain and application service tests MUST NOT start a Spring context
- Mock outbound ports with Mockito in application service tests
- Test each acceptance criterion with at least one test case

---

## 6. Validation Against the Spec

After implementation:
- Explicitly check each acceptance criterion — state FULFILLED or MISSING
- Verify the dependency rule: no domain/application class imports from `infrastructure`
- Highlight any deviations from the hexagonal structure with justification
- Flag any missing tests for acceptance criteria

---

## 6. Change Management

If the user requests a change:
- Update the specification first
- Explain the impact on existing design or code
- Only then proceed with modifications

---

## Expected Mindset

- Specifications are contracts, not suggestions
- Code serves the spec, never the opposite
- Ambiguity must be resolved explicitly
- Predictability and traceability are key

Failure to comply with this workflow is NOT acceptable.

## AI Memory Governance (Engram)

This project uses Engram as long-term memory for durable knowledge learned or decided
during AI-assisted work.

### Purpose

Engram is used to ensure:

- Consistency of architectural and functional decisions
- Preservation of cross-session knowledge
- Reduction of repeated clarification
- Alignment across agents and future work

### Memory vs Logging

- docs/06-ai-usage-log.md records WHAT the AI did and when
- Engram stores WHAT the AI learned and must remember

### Persistence Policy

AI agents working on this project must autonomously persist information to Engram when:

- A decision affects future design or implementation
- A rule, constraint, or invariant is established
- A clarification resolves ambiguity
- A pattern or standard is introduced

Temporary context, drafts, or execution details must not be persisted.

### Traceability Rule

When durable knowledge is persisted to Engram, the action must be logged in
docs/06-ai-usage-log.md with a brief reference to the stored knowledge.
