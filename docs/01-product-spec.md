# Product Specification — RUMBO

## 1. Vision

**RUMBO** is a gamified financial education platform targeted at individuals seeking to improve their personal finance knowledge and habits. The platform personalises the learning journey based on the user's vital stage and financial context, tracking individual progress and motivating completion through competitive rankings.

The platform is publicly accessible — no banking relationship is required to register. It operates independently of any corporate SSO or external authentication system.

---

## 2. Problem Statement

Many people lack the financial literacy needed to make sound personal decisions. Existing educational resources are generic, static, and fail to adapt to the learner's specific life stage. RUMBO solves this by combining structured learning paths, personalised profiling, and competitive mechanics (rankings, gamification) to make financial education engaging and measurable.

---

## 3. Target Users

| Role | Description |
|------|-------------|
| **Unregistered visitor** | Any person who reaches the platform for the first time and wants to start learning |
| **Registered user** | A person with an account who progresses through modules and accumulates score |
| **Administrator** | Internal operator who manages content and reviews usage metrics |

### User Profiles (Learning Personas)

The platform segments users into four orientative profiles. Selection is voluntary and not age-restricted.

| Profile | Typical age | Context |
|---------|------------|---------|
| Explorador Digital | 18–23 | First steps into financial independence, digital-native |
| Primera Nómina | 23–30 | First stable income, building good habits from scratch |
| Emprendedor / Autónomo | 25–40 | Self-employed, irregular income, tax management needs |
| Constructor de Futuro | 30–40 | Consolidating savings, investment planning, long-term goals |

---

## 4. Core Features

### 4.1 Authentication & Onboarding

| # | User Story | Priority | Status |
|---|-----------|----------|--------|
| US-001 | Registro de nuevo usuario | Must Have | ✅ Implemented |
| US-002 | Login de usuario registrado | Must Have | ✅ Implemented |
| US-003 | Selección de perfil de usuario | Must Have | Pending |
| US-004 | Test de idoneidad inicial | Must Have | Pending |

**Key behaviours:**
- Registration collects: email, nombre, apellidos, edad. Password stored with BCrypt.
- Login issues a JWT. Error message is always generic ("Email o contraseña incorrectos") — never reveals which field is wrong.
- Profile selection is performed once after first registration and determines the learning itinerary.
- Initial suitability test assigns the user to the correct module level within their profile.

### 4.2 Learning Itinerary

| # | User Story | Priority | Status |
|---|-----------|----------|--------|
| US-005 | Completar módulo de aprendizaje | Must Have | Pending |
| US-006 | Ver itinerario personalizado | Must Have | Pending |

**Key behaviours:**
- Each profile has a curated set of modules organised in progressive levels: básico → intermedio → avanzado.
- Transversal content (basic financial literacy, money psychology) appears across all profiles.
- Module state persists between sessions: pending / in-progress / completed / failed.
- The itinerary is the primary dashboard — users navigate the entire platform from it.

### 4.3 Ranking & Gamification

| # | User Story | Priority | Status |
|---|-----------|----------|--------|
| US-007 | Ver ranking global | Should Have | Pending |
| US-008 | Módulo de desempate en ranking | Should Have | Pending |
| US-009 | Identificación de ganadores | Could Have | Pending |

**Key behaviours:**
- Global ranking scores users by: percentage of correct answers × completion time.
- The user's own position is highlighted in the ranking table.
- A tiebreaker module is triggered when multiple users share the same score and time.
- Winners are identified and recognisable within the platform.

### 4.4 Administration

| # | User Story | Priority | Status |
|---|-----------|----------|--------|
| US-010 | Gestión de contenidos (admin) | Must Have | Pending |
| US-011 | Métricas de uso (admin) | Should Have | Pending |

**Key behaviours:**
- Administrators can create, update, and deactivate learning modules.
- Usage metrics dashboard shows: active users, completion rates, module performance, ranking distribution.

---

## 5. Acceptance Criteria Summary

All features trace back to User Stories in `functional/US/`. Each AC maps directly to a spec scenario in the SDD pipeline.

| US | AC | Summary | Implemented |
|----|-----|---------|-------------|
| US-001 | AC-01 | Successful registration → redirected to profile selection | ✅ |
| US-001 | AC-02 | Duplicate email → error message, form preserved | ✅ |
| US-001 | AC-03 | Invalid / missing fields → inline validation errors | ✅ |
| US-002 | AC-01 | Valid credentials → JWT issued, redirect to dashboard | ✅ |
| US-002 | AC-02 | Wrong credentials → generic error, password cleared, email preserved | ✅ |
| US-002 | AC-03 | Arrival from external link → no SSO, own auth only | ✅ |

---

## 6. Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| **Performance** | Register endpoint < 500ms. Login endpoint < 300ms. Under normal load. |
| **Security** | Passwords hashed with BCrypt. JWT stateless auth. No credentials in logs. No SSO with external systems. UUID primary keys (no enumeration attacks). |
| **Privacy (RGPD)** | Minimum data collected. Informed consent on registration. No financial or banking data collected. |
| **Scalability** | UUID keys enable sharding and read replicas without ID collision. |
| **Accessibility** | All forms keyboard-navigable. ARIA labels on form fields. |
| **Browser support** | Modern evergreen browsers (Chrome, Firefox, Safari, Edge). |

---

## 7. Out of Scope (MVP)

- Email verification via confirmation link
- Password recovery / reset flow
- Login with social providers (Google, GitHub, etc.)
- SSO with any banking or corporate system
- Native mobile apps
- Account lockout after multiple failed login attempts (deferred to MVP+)
- "Remember me" / persistent session

---

## 8. Success Metrics

| Metric | Target |
|--------|--------|
| User registration conversion | > 60% of visitors who start the form complete it |
| Module completion rate | > 40% of registered users complete at least one module |
| Return rate | > 30% of users return within 7 days of registration |
| API error rate | < 1% of requests result in 5xx errors |
| Backend test coverage | ≥ 80% on `application/` and `domain/` packages |
| Frontend test coverage | ≥ 70% on services and page components |

---

## 9. Constraints

- Platform is web-only (Angular SPA + Spring Boot REST API)
- Database: PostgreSQL 15 (Docker in local dev, managed instance in production)
- No integration with external banking systems, under any circumstances
- All IDs are UUID (never autoincrement integers) — security and scalability requirement
- RGPD compliance is mandatory from day one

---

## 10. Glossary

| Term | Definition |
|------|-----------|
| **Itinerario** | The personalised learning path assigned to a user based on their profile |
| **Módulo** | A discrete learning unit within an itinerary, containing theory + test |
| **Perfil** | One of 4 user archetypes that determines the assigned itinerary |
| **Test de idoneidad** | Initial assessment that places the user at the correct level within their profile |
| **Ranking** | Global leaderboard scoring users by aciertos % and completion time |
| **JWT** | JSON Web Token — stateless auth token issued on login |

---

Last updated: 2026-05-04
