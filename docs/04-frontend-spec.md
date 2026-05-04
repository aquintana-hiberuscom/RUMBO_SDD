# Frontend Specification

## Technology Stack

- **Framework**: Angular 17 (standalone components)
- **Language**: TypeScript 5.2, strict mode enabled
- **Styling**: SCSS (component-scoped + global `styles.scss`)
- **HTTP**: `HttpClientModule` + `AuthInterceptor` (adds JWT Bearer token)
- **Reactive**: RxJS — Observables in services, `async` pipe in templates
- **Routing**: `app.routes.ts` with lazy-loaded page routes + `AuthGuard`
- **Build**: Angular CLI (`ng serve` :4200, `ng build` for production)
- **Testing**: Karma + Jasmine, `HttpClientTestingModule` for HTTP mocks

---

## API Integration

The frontend consumes the Spring Boot REST API defined in [02-technical-spec.md](02-technical-spec.md) and [03-api-contract.md](03-api-contract.md).

- **Base URL**: `http://localhost:8080/api/v1` (dev) — configured via `environment.ts`
- **Auth**: JWT Bearer token. `AuthInterceptor` attaches it to every request automatically.
- **Response envelope**: All responses follow `{ success, data, message, timestamp }`. Services unwrap `data` before exposing to components.
- **Error handling**: `ErrorInterceptor` catches HTTP errors and maps them to user-facing messages.

---

## Project Structure

```
src/app/
├── components/          # Reusable UI components (standalone)
│   ├── button/
│   ├── form-field/
│   └── loading/
├── pages/               # Page-level components (one per route)
│   ├── auth/
│   │   ├── login/
│   │   └── register/
│   ├── dashboard/
│   └── profile/
├── services/            # HTTP + state services
│   ├── auth.service.ts
│   └── user.service.ts
├── models/              # TypeScript interfaces (mirror API DTOs)
│   ├── user.model.ts
│   └── api-response.model.ts
├── guards/
│   └── auth.guard.ts    # Protects authenticated routes
├── interceptors/
│   ├── auth.interceptor.ts    # Attaches JWT token
│   └── error.interceptor.ts   # Global error handling
├── app.component.*
├── app.routes.ts        # Route definitions
└── app.config.ts        # provideRouter, provideHttpClient, interceptors
```

---

## Component Architecture

```
AppComponent
├── pages/
│   ├── auth/
│   │   ├── LoginComponent       → calls AuthService.login()
│   │   └── RegisterComponent    → calls AuthService.register()
│   ├── dashboard/
│   │   └── DashboardComponent   → calls UserService / stats services
│   └── profile/
│       └── ProfileComponent     → calls UserService.getProfile() / update()
└── components/ (shared)
    ├── ButtonComponent
    ├── FormFieldComponent
    └── LoadingComponent
```

**Rules:**
- Page components are routed and may inject services directly
- Shared components are purely presentational — inputs/outputs only, no service injection
- All components are `standalone: true`

---

## State Management

- **No global store** — services hold state via `BehaviorSubject` / `signal()`
- `AuthService` owns JWT token and current user state
- Components subscribe via `async` pipe; avoid manual `subscribe()` in components
- Local UI state (loading, error messages) lives in the component itself

---

## Design System

### Colors
- Primary: `#007BFF` (Blue)
- Secondary: `#6C757D` (Gray)
- Success: `#28A745` (Green)
- Danger: `#DC3545` (Red)
- Warning: `#FFC107` (Yellow)

### Typography
- Headings: Roboto 600, 2rem
- Body: Roboto 400, 1rem
- Small: Roboto 400, 0.875rem

### Spacing
- Base unit: 8px
- Padding / Margins: 8, 16, 24, 32px

### Responsive Breakpoints
- Mobile: < 576px
- Tablet: 576px – 992px
- Desktop: > 992px

---

## Pages

### Authentication
- Login page — email + password form, calls `POST /api/v1/auth/login`, stores JWT
- Register page — with reactive form validation, calls `POST /api/v1/auth/register`
- On auth error, display message from API envelope `message` field

### Dashboard
- Welcome message with current user's name
- Quick stats (data from backend)
- Recent activity list

### User Profile
- Display profile info (from `GET /api/v1/users/me`)
- Edit profile form (calls `PUT /api/v1/users/me`)
- Change password
- Logout button — clears JWT, redirects to `/login`

---

## Testing Standards

| Layer | Scope | Tools |
|-------|-------|-------|
| Services | Unit — mock `HttpClient` | `HttpClientTestingModule`, Jasmine |
| Components | Unit — no real HTTP | `TestBed`, `HttpClientTestingModule` |
| Guards / Interceptors | Unit — mock router/token | Jasmine spies |
| Full page flows | E2E (optional) | Cypress |

- Minimum coverage: **70%** on services and page components
- Mock all HTTP requests — no real API calls in tests
- Test services and components separately

---

Last updated: 2026-05-04
