# RUMBO - Agent Instructions

Comprehensive guide for AI agents contributing to RUMBO, a Python Flask + Angular full-stack application.

## Quick Reference

| Aspect | Details |
|--------|---------|
| **Backend** | Python 3.9+, Flask 2.x, SQLAlchemy, PostgreSQL, pytest |
| **Frontend** | Angular 17, TypeScript 5.2, SCSS, RxJS, Karma/Jasmine |
| **Database** | PostgreSQL (Docker), migrations via Flask-Migrate |
| **API** | RESTful, JWT auth, base: `/api/v1`, responses: JSON envelopes |
| **Dev Server** | Backend: `flask run` (5000), Frontend: `ng serve` (4200) |
| **Docker** | `docker-compose up` starts all services |
| **Tests** | Backend: `pytest`, Frontend: `npm test` |

## Architecture

**Three-tier stack**:
- **Frontend** (Angular): `/frontend/src/app/` — standalone components, RxJS services
- **API** (Flask): `/backend/app/` — routes, models, schemas, services
- **Database** (PostgreSQL): runs in Docker, connected via SQLAlchemy ORM

**API Contract**: See [docs/03-api-contract.md](docs/03-api-contract.md) for endpoints, auth (JWT Bearer), and response envelopes.

## Agent Roles & Responsibilities

### Backend Agent (Python/Flask)
**Owns**: `/backend/app/` and `/backend/tests/`

- **Build/Test**: Always run `pytest` before committing. Use `pytest --cov=app` for coverage.
- **Architecture**: Routes in `app/routes/`, ORM models in `app/models/`, validation schemas in `app/schemas/`, business logic in `app/services/`
- **Database**: Use SQLAlchemy ORM. Migrations via Flask-Migrate (`flask db migrate`, `flask db upgrade`).
- **API Design**: Follow contract in [docs/03-api-contract.md](docs/03-api-contract.md). Responses must use JSON envelope format (success, data, message, timestamp).
- **Auth**: JWT (Flask-JWT-Extended). Endpoints require `@jwt_required()`. Test with Bearer token in Authorization header.
- **Dependencies**: Update `backend/requirements.txt` when adding packages. Run `pip install -r requirements.txt` after changes.
- **Cross-team**: Notify Frontend Agent of any API contract changes immediately.

**File Structure**:
```
backend/
├── app/
│   ├── __init__.py            # Flask app creation
│   ├── models/                # SQLAlchemy models
│   ├── routes/                # API endpoints
│   ├── schemas/               # Marshmallow schemas for validation
│   ├── services/              # Business logic (reusable)
│   └── utils/                 # Helpers (auth, errors, etc.)
├── tests/
│   ├── conftest.py            # pytest fixtures
│   ├── test_auth.py           # Auth tests
│   └── test_*.py              # Feature tests
├── requirements.txt           # Python dependencies
└── .env.example              # Environment template
```

### Frontend Agent (Angular)
**Owns**: `/frontend/src/` and frontend tests

- **Build/Test**: Always run `npm test` before committing. Use `npm run test -- --code-coverage` for coverage.
- **Architecture**: Standalone components in `src/app/components/`, page components in `src/app/pages/`, services in `src/app/services/`.
- **HTTP & Auth**: Use `HttpClientModule` with interceptors. `AuthService` handles JWT tokens. `AuthInterceptor` adds Bearer token to all requests.
- **Styling**: SCSS in component files. Global styles in `src/styles.scss`. Follow design system in [docs/04-frontend-spec.md](docs/04-frontend-spec.md).
- **Routing**: Define routes in `app/app.routes.ts`. Use `AuthGuard` to protect authenticated routes.
- **Dependencies**: Update `frontend/package.json` when adding packages. Run `npm install` after changes.
- **Cross-team**: Test against API contract. Report API discrepancies to Backend Agent immediately.

**File Structure**:
```
frontend/src/
├── app/
│   ├── components/            # Reusable UI components
│   ├── pages/                 # Page-level components
│   ├── services/              # HTTP & state services
│   ├── models/                # TypeScript interfaces
│   ├── guards/                # Route guards (auth)
│   ├── interceptors/          # HTTP interceptors (auth, error handling)
│   ├── app.component.*        # Root component
│   ├── app.routes.ts          # Route definitions
│   └── app.config.ts          # App configuration
├── assets/                    # Static files
└── styles.scss                # Global styles
```

### DevOps Agent
**Owns**: `docker-compose.yml`, CI/CD, deployment

- **Local Dev**: `docker-compose up -d` starts PostgreSQL, Flask, Angular. Logs: `docker-compose logs -f [service]`.
- **Database**: PostgreSQL 15 Alpine in container, credentials in `docker-compose.yml`.
- **Volumes**: Backend and frontend src mapped for hot-reload.
- **Networking**: Services communicate via `rumbo_network`. Frontend at `:4200`, backend at `:5000`.
- **Environment**: Copy `.env.example` to `.env` in each directory before running services.

## Workflow

1. **Before starting**: Review specs in `/docs/` (especially 03-api-contract.md and 02-technical-spec.md)
2. **Implementation**:
   - Backend: Write service logic, then routes. Add tests immediately.
   - Frontend: Build components from API contract. Use services for HTTP.
3. **Testing**: Run full test suite locally before commit.
4. **Documentation**: Update `/docs/06-ai-usage-log.md` with session summary. Link to code, not copy docs.

## Key Conventions

- **Commits**: Use conventional format: `feat(backend):`, `fix(frontend):`, `test:`, `docs:`, etc.
- **API Responses**: Always use JSON envelope:
  ```json
  {
    "success": true,
    "data": { /* payload */ },
    "message": "Human-readable message",
    "timestamp": "2026-05-04T10:30:00Z"
  }
  ```
- **Error Handling**: Backend returns error code + message. Frontend shows user-friendly message.
- **Authentication**: JWT Bearer token. Issued on login, validated on protected endpoints.
- **CORS**: Backend allows `http://localhost:4200` in development. Update for production.
- **Database**: No direct SQL. Use SQLAlchemy ORM. Migrations tracked in Git.
- **Imports**: Backend uses absolute imports from app root. Frontend uses path aliases in tsconfig.

## Common Tasks

### Start a new feature (always first step)
1. Write the User Story: trigger the `us-writer` skill or run `/sdd-new` and reference an existing US
   ```
   functional/US/US-{NNN}-{slug}.md
   ```
2. Verify it passes INVEST (the skill does this automatically)
3. Only then proceed to SDD pipeline: `US → Proposal → Spec → Design → Tasks → Implementation`

**Rule**: no feature development without a User Story. A feature description in chat is NOT a US.

### Develop a feature (SDD pipeline)
```
1. Write US → functional/US/ (us-writer skill)
2. Explore  → /sdd-new {change-name} (references the US)
3. Propose  → sdd-propose reads the US and fills Source User Stories
4. Spec     → sdd-spec maps US AC-XX → spec scenarios + updates US traceability table
5. Design   → sdd-design (hexagonal layer mapping)
6. Tasks    → sdd-tasks (atomic implementation checklist)
7. Apply    → sdd-apply (code, tests)
8. Verify   → sdd-verify (against spec + acceptance criteria)
9. Archive  → sdd-archive
```

### Backend: Add a new API endpoint
1. Create model in `app/models/` (if needed)
2. Create schema in `app/schemas/` for validation
3. Create service in `app/services/` with business logic
4. Create route in `app/routes/`
5. Add tests in `tests/test_<feature>.py`
6. Run `pytest` to verify
7. Update [docs/03-api-contract.md](docs/03-api-contract.md) with new endpoint

### Frontend: Add a new component
1. Generate: `ng generate component <name>` (or create manually in `src/app/components/`)
2. Import as standalone: `@Component({ standalone: true, imports: [...] })`
3. Add to route in `app.routes.ts` if it's a page
4. Use `HttpClientService` to call backend API
5. Add tests in `<name>.component.spec.ts`
6. Run `npm test` to verify

### Database: Add a migration
1. Update model in `app/models/`
2. Run: `flask db migrate -m "description"`
3. Review generated migration file
4. Run: `flask db upgrade`
5. Commit both model and migration

## Cross-Team Collaboration Points

| Scenario | Owner | Notifies |
|----------|-------|----------|
| API contract change | Backend | Frontend |
| Breaking frontend change | Frontend | Backend (for future APIs) |
| Database schema change | Backend | DevOps (update Docker init scripts) |
| New environment variable | Any | All (update `.env.example`) |
| Dependency upgrade | Any | All (commit lock files) |

## Testing Standards

- **Backend**: Minimum 80% code coverage. Use pytest fixtures in `tests/conftest.py`. Test auth with JWT.
- **Frontend**: Minimum 70% code coverage. Mock HTTP requests in tests. Test services and components separately.
- **Run before every commit**:
  ```bash
  # Backend
  cd backend && pytest --cov=app
  
  # Frontend
  cd frontend && npm test -- --watch=false --code-coverage
  ```

## Documentation Links

- [Product Specification](docs/01-product-spec.md) — What we're building
- [Technical Specification](docs/02-technical-spec.md) — Architecture & stack
- [API Contract](docs/03-api-contract.md) — Endpoints, auth, errors
- [Frontend Specification](docs/04-frontend-spec.md) — Components, design, state
- [Test Plan](docs/05-test-plan.md) — Testing strategy
- [AI Usage Log](docs/06-ai-usage-log.md) — Track agent sessions
- [SDD Skill](docs/07-sdd-spec.md) — Spec-Driven Development rules (includes US Step 0)
- [User Stories](functional/US/) — All feature US files

## Troubleshooting

| Issue | Solution |
|-------|----------|
| Database connection error | Ensure `docker-compose up` is running. Check `DATABASE_URL` in `.env`. |
| CORS error in frontend | Backend CORS needs `http://localhost:4200`. Check `CORS_ORIGINS` in backend `.env`. |
| Port conflict | Change in `docker-compose.yml` or `.env`. Or: `docker-compose up -p 8080:5000`. |
| Module import error | Check virtual env is activated (backend) or `npm install` run (frontend). |
| Test failure | Run specific test file to see full error. Use `pytest -v` or `npm test -- --verbose`. |

---
**Last updated**: 2026-05-04 | **Status**: Active Development
