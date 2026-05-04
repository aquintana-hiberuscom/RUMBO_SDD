# Test Plan

> This plan covers backend (Java 21 + Spring Boot 3.2) and frontend (Angular 17) testing.
> Aligned with [02-technical-spec.md](02-technical-spec.md) and [04-frontend-spec.md](04-frontend-spec.md).

---

## Backend Testing Strategy

The backend follows hexagonal architecture. Test type is determined by the layer under test — never start a Spring context when a unit test is sufficient.

### Layer test matrix

| Layer | Test type | Tools | Spring context | Min coverage |
|-------|-----------|-------|----------------|-------------|
| `domain/model` | Unit | JUnit 5, AssertJ | None | 100% |
| `application/service` | Unit | JUnit 5, Mockito | None | 80% |
| `infrastructure/.../persistence` | Integration | `@DataJpaTest`, H2 | Slice | 80% |
| `infrastructure/.../web` | Slice | `@WebMvcTest`, MockMvc | Slice | 80% |
| Full flow | Integration | `@SpringBootTest` | Full | Key flows only |

### Rules
- Domain and application service tests MUST NOT load a Spring context (`@ExtendWith(MockitoExtension.class)` only)
- Mock outbound ports with Mockito in application service tests
- Use `@DataJpaTest` + H2 for persistence adapters — no full context
- Use `@WebMvcTest` + `MockMvc` for web adapters — mock the inbound port with `@MockBean`
- `@SpringBootTest` only for integration tests that need the full wiring
- Constructor injection in all production code — simplifies test instantiation

### Test commands

```bash
# Run all tests
./mvnw clean test

# Run with JaCoCo coverage report
./mvnw clean verify

# Run a specific test class
./mvnw test -Dtest=UserServiceTest

# Run only unit tests (exclude @SpringBootTest)
./mvnw test -Dgroups="unit"

# Run only integration tests
./mvnw test -Dgroups="integration"
```

### Coverage gate
- Minimum: **80%** on `application/` and `domain/` packages (enforced by JaCoCo in `mvn verify`)
- Coverage report: `target/site/jacoco/index.html`

### Test location

```
src/test/java/com/rumbo/
├── domain/              # Pure unit tests — no Spring
├── application/service/ # Unit tests with Mockito mocks
├── infrastructure/
│   ├── adapter/in/web/  # @WebMvcTest slices
│   └── adapter/out/persistence/  # @DataJpaTest slices
└── integration/         # @SpringBootTest full-context tests
```

---

## Frontend Testing Strategy

### Layer test matrix

| Layer | Test type | Tools | Scope |
|-------|-----------|-------|-------|
| Services | Unit | Jasmine, `HttpClientTestingModule` | Mock all HTTP |
| Page components | Unit | `TestBed`, Jasmine | Mock services |
| Shared components | Unit | `TestBed`, Jasmine | Input/output only |
| Guards / Interceptors | Unit | Jasmine spies, `RouterTestingModule` | Mock dependencies |
| E2E (optional) | E2E | Cypress | Critical flows |

### Rules
- No real HTTP calls in tests — always use `HttpClientTestingModule`
- Test services and components separately
- Page components: mock injected services with `jasmine.createSpyObj`
- Shared components: test `@Input` / `@Output` bindings only
- Test the API envelope unwrapping: services expose `data`, not the raw response

### Test commands

```bash
# Run all tests once (CI mode)
npm test -- --watch=false

# Run with coverage report
npm test -- --watch=false --code-coverage

# Run a specific spec file
npm test -- --include="src/app/services/auth.service.spec.ts"

# Run E2E tests (Cypress)
npm run e2e
```

### Coverage gate
- Minimum: **70%** on services and page components
- Coverage report: `coverage/rumbo-frontend/index.html`

---

## Integration / API Contract Tests

Verify the frontend–backend contract defined in [03-api-contract.md](03-api-contract.md).

- All API responses match the envelope `{ success, data, message, timestamp }`
- Auth endpoints return a valid JWT on success
- Protected endpoints return `401` without a token, `403` on insufficient permissions
- Error responses include an `error` code and a human-readable `message`
- Backend CORS allows `http://localhost:4200` in development

Run these as `@SpringBootTest` integration tests on the backend using `MockMvc` or `TestRestTemplate`.

---

## Regression Test Checklist

- [ ] Login with valid credentials → JWT returned, stored, subsequent requests authenticated
- [ ] Login with invalid credentials → `401`, error message shown in UI
- [ ] Register new user → user created, redirected to dashboard
- [ ] Access protected route without token → redirected to `/login`
- [ ] Token expiry → interceptor handles `401`, clears session, redirects
- [ ] Profile update → changes persisted, response reflects updated data
- [ ] CORS — frontend at `:4200` can reach backend at `:8080`
- [ ] API error response → UI shows message from envelope `message` field

---

## Performance Targets

| Metric | Target |
|--------|--------|
| API response time (p95) | < 500ms |
| Frontend initial bundle | < 500KB gzipped |
| `@SpringBootTest` startup | < 30s |
| DB query (simple CRUD) | < 50ms |

---

## Manual Testing Checklist

- [ ] Cross-browser: Chrome, Firefox, Safari, Edge
- [ ] Mobile responsiveness (< 576px breakpoint)
- [ ] Form validation messages visible and correct
- [ ] Loading states shown during HTTP calls
- [ ] Error states shown on API failure

---

Last updated: 2026-05-04
