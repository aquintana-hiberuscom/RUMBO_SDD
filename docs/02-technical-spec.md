# Technical Specification

## Architecture Overview

```
┌─────────────┐
│   Angular   │
│  Frontend   │
└──────┬──────┘
       │ HTTP/REST
┌──────▼──────────────────────┐
│   Spring Boot API (Java)    │
│   Hexagonal Architecture    │
│                             │
│  ┌───────────────────────┐  │
│  │  Web Adapters         │  │  ← @RestController
│  │  (infrastructure/in)  │  │
│  └──────────┬────────────┘  │
│             │ inbound port  │
│  ┌──────────▼────────────┐  │
│  │  Application Services │  │  ← @Service
│  │  (application/service)│  │
│  └──────────┬────────────┘  │
│             │ outbound port │
│  ┌──────────▼────────────┐  │
│  │  Persistence Adapters │  │  ← @Repository
│  │  (infrastructure/out) │  │
│  └───────────────────────┘  │
└──────┬──────────────────────┘
       │ JPA / SQL
┌──────▼──────────┐
│   PostgreSQL    │
│   Database      │
└─────────────────┘
```

## Technology Stack

### Backend
- **Language**: Java 21 (LTS)
- **Framework**: Spring Boot 3.2.x
- **Architecture**: Hexagonal (Ports & Adapters)
- **Web**: Spring MVC (`spring-boot-starter-web`)
- **Persistence**: Spring Data JPA (`spring-boot-starter-data-jpa`) + Hibernate 6
- **Database**: PostgreSQL (production), H2 (tests)
- **Validation**: Bean Validation (`spring-boot-starter-validation`)
- **Security**: Spring Security + JWT (`spring-boot-starter-security`)
- **Build tool**: Maven 3.9+
- **Testing**: JUnit 5, Mockito, AssertJ, `@SpringBootTest`, `@DataJpaTest`, `@WebMvcTest`
- **Utilities**: Lombok

### Backend Package Structure (Hexagonal)

```
com.rumbo/
├── domain/
│   └── model/          # Entities, Value Objects, Domain Events — no framework deps
├── application/
│   ├── port/
│   │   ├── in/         # Inbound ports (use case interfaces)
│   │   └── out/        # Outbound ports (repository/service interfaces)
│   └── service/        # Use case implementations (@Service)
└── infrastructure/
    ├── adapter/
    │   ├── in/
    │   │   └── web/    # REST controllers (@RestController)
    │   └── out/
    │       └── persistence/  # JPA adapters (@Repository)
    └── config/         # Spring configuration (@Configuration)
```

### Frontend
- **Framework**: Angular 17
- **Language**: TypeScript 5.2
- **Package Manager**: npm
- **Styling**: SCSS
- **HTTP Client**: `HttpClientModule` + interceptors
- **Reactive**: RxJS

### Infrastructure
- **Containerization**: Docker
- **Orchestration**: Docker Compose
- **CI/CD**: [To be defined]

## Database Schema

### Users Table
- `id` (UUID, primary key)
- `email` (string, unique, not null)
- `password_hash` (string, not null)
- `created_at` (timestamp)
- `updated_at` (timestamp)

### [Additional Tables]
- [To be defined based on features]

## Testing Strategy

| Layer | Test type | Tools | Spring context |
|-------|-----------|-------|----------------|
| Domain model | Unit | JUnit 5, AssertJ | None |
| Application service | Unit | JUnit 5, Mockito | None |
| Persistence adapter | Integration | `@DataJpaTest`, H2 | Slice |
| Web adapter | Slice | `@WebMvcTest`, MockMvc | Slice |
| Full flow | Integration | `@SpringBootTest` | Full |

- **Minimum coverage**: 80% on `application/` and `domain/` packages
- Domain and application service tests MUST NOT start a Spring context
- Use constructor injection; never `@Autowired` on fields

## API Response Format

All API responses follow this envelope format:

```json
{
  "success": true,
  "data": { /* response payload */ },
  "message": "Operation successful",
  "timestamp": "2026-05-04T10:30:00Z"
}
```

Error responses:

```json
{
  "success": false,
  "error": "ERROR_CODE",
  "message": "Human readable error message",
  "timestamp": "2026-05-04T10:30:00Z"
}
```

---
Last updated: 2026-05-04
