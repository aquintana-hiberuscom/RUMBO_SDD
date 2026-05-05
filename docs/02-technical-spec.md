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
- **Utilities**: Lombok, MapStruct 1.5.x

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

### Convención de IDs (OBLIGATORIO)

Todas las tablas usan **UUID** como clave primaria, **nunca BIGINT autoincremental**.

- Tipo en PostgreSQL: `UUID`
- Tipo en Java (dominio y JPA): `java.util.UUID`
- Generación: `@GeneratedValue(strategy = GenerationType.UUID)` (Hibernate 6 nativo)
- Razón: evita enumeration attacks, facilita sharding y réplicas, IDs generables en cliente

Ejemplo JPA entity:
```java
@Id
@GeneratedValue(strategy = GenerationType.UUID)
@Column(columnDefinition = "uuid", updatable = false, nullable = false)
private UUID id;
```

Ejemplo Liquibase changeset:
```yaml
- column:
    name: id
    type: UUID
    constraints:
      primaryKey: true
      nullable: false
```

### Users Table
- `id` (UUID, primary key)
- `email` (varchar 255, unique, not null)
- `nombre` (varchar 255, not null)
- `apellidos` (varchar 255, not null)
- `edad` (integer, not null)
- `password_hash` (varchar 255, not null)

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

## Testing Policy (Mandatory)

All deliverables in this project must include automated tests appropriate to their
responsibility and level.

### General Rule

Every service, repository, module, or executable component MUST include tests.
Code without tests is considered incomplete.

### Test Types by Component

- **Domain logic, use cases, pure services**
  - MUST include unit tests
  - External dependencies must be mocked

- **Infrastructure components (repositories, adapters, gateways)**
  - MUST include integration tests when applicable
  - Database or external systems may be real or test containers

- **APIs / Controllers**
  - MUST include integration or slice tests
  - Request/response behaviour must be validated

- **Frontend components**
  - MUST include unit and/or component tests
  - Critical flows must be covered


### Exception Policy

Exceptions are allowed only when:
- Explicitly justified
- Documented in the code or specification
- Approved as technical debt

## Coding Conventions

### Lombok (OBLIGATORIO en todas las clases Java)

Usar Lombok en todas las clases para eliminar boilerplate. Anotaciones por tipo de clase:

| Tipo | Anotaciones obligatorias |
|------|-------------------------|
| Domain model | `@Data @Builder @NoArgsConstructor @AllArgsConstructor` |
| JPA Entity | `@Data @Builder @NoArgsConstructor @AllArgsConstructor` |
| `@Service` / `@Component` / `@RestController` | `@RequiredArgsConstructor` |
| Clases con logging | `@Slf4j` |

Reglas:
- **NUNCA** escribir getters, setters, constructores o `toString` a mano si Lombok puede generarlos
- `@RequiredArgsConstructor` en servicios y controladores reemplaza la inyección `@Autowired`
- En el `pom.xml`, Lombok debe declararse en `annotationProcessorPaths` **antes** que MapStruct

### MapStruct (OBLIGATORIO para conversiones entre capas)

Todas las conversiones entre objetos de distintas capas se hacen con **MapStruct**, nunca con código manual.

| Conversión | Mapper | Ubicación |
|-----------|--------|----------|
| `User` ↔ `UserJpaEntity` | `UserEntityMapper` | `infrastructure/adapter/out/persistence/` |
| `RegisterUserResult + token` → `RegisterResponse` | `RegisterUserMapper` | `infrastructure/adapter/in/web/` |

Reglas:
- Todos los mappers son interfaces con `@Mapper(componentModel = "spring")` → Spring los inyecta como beans
- Si los nombres de campos coinciden entre origen y destino, no hace falta `@Mapping` explícito
- Si hay múltiples fuentes (e.g. resultado + token), usar `@Mapping(source = "objeto.campo", target = "campo")`
- **NUNCA** usar constructores manuales o `BeanUtils.copyProperties` para copiar entre capas
- El `maven-compiler-plugin` debe tener `annotationProcessorPaths` con Lombok primero y MapStruct segundo

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
