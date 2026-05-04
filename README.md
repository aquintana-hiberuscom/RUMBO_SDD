# RUMBO

Plataforma gamificada de educación financiera. Stack: **Java 17 + Spring Boot 3.2**, **Angular 17**, **PostgreSQL 15**.

## Requisitos previos

| Herramienta | Versión mínima |
|-------------|---------------|
| Java        | 17             |
| Maven       | 3.6            |
| Node.js     | 18             |
| Docker Desktop | cualquiera  |

---

## Arranque en local

### 1. Base de datos (PostgreSQL vía Docker)

```bash
docker-compose up db
```

Esto levanta un PostgreSQL 15 en `localhost:5432` con:
- Base de datos: `rumbo_db`
- Usuario: `rumbo_user`
- Contraseña: `rumbo_password`

Al arrancar Spring Boot por primera vez, **Liquibase crea automáticamente todas las tablas**.

> Primera vez o si hay que resetear datos:
> ```bash
> docker-compose down -v   # elimina el volumen con los datos
> docker-compose up db
> ```

### 2. Backend (Spring Boot · puerto 8080)

```bash
cd backend
mvn spring-boot:run
```

Spring Boot conecta a PostgreSQL, ejecuta las migraciones pendientes de Liquibase y arranca la API.

Endpoints disponibles:
- `POST /api/v1/auth/register` — registro de usuario
- `GET  /api/health` — health check

### 3. Frontend (Angular · puerto 4200)

```bash
cd frontend
npm install        # solo la primera vez
npm start
```

Abre `http://localhost:4200` → redirige a `/registro`.

---

## Estructura del proyecto

```
RUMBO/
├── backend/
│   ├── src/main/java/com/rumbo/
│   │   ├── domain/model/              # Entidades de dominio (sin dependencias de framework)
│   │   ├── application/
│   │   │   ├── port/in/               # Casos de uso (interfaces)
│   │   │   ├── port/out/              # Puertos de salida (repositorios)
│   │   │   └── service/               # Implementaciones de casos de uso
│   │   └── infrastructure/
│   │       ├── adapter/in/web/        # Controladores REST + DTOs + Mappers
│   │       ├── adapter/out/persistence/ # JPA entities, adapters, mappers
│   │       └── config/                # Security, JWT
│   ├── src/main/resources/
│   │   ├── application.properties
│   │   └── db/changelog/              # Migraciones Liquibase (YAML)
│   └── src/test/
│       ├── java/                      # Tests unitarios e integración
│       └── resources/application.properties  # Config de test (H2)
├── frontend/
│   └── src/app/
│       ├── models/                    # Interfaces TypeScript
│       ├── services/                  # Servicios HTTP
│       └── pages/                    # Componentes de página
├── functional/US/                     # User Stories
├── docs/                              # Especificaciones técnicas y funcionales
└── docker-compose.yml
```

---

## Tests

```bash
# Backend (usa H2 en memoria — no necesita Docker)
cd backend && mvn test

# Frontend
cd frontend && npm test -- --watch=false
```

---

## Migraciones de base de datos (Liquibase)

Las migraciones están en `backend/src/main/resources/db/changelog/changes/`.

Se ejecutan **automáticamente al arrancar** Spring Boot. Para añadir una nueva migración:
1. Crear `NNN-descripcion.yaml` en la carpeta `changes/`
2. Incluirla en `db.changelog-master.yaml`
3. Arrancar el backend — Liquibase la aplica sola

---

## Variables de entorno relevantes

| Variable | Por defecto (local) | Descripción |
|----------|---------------------|-------------|
| `spring.datasource.url` | `jdbc:postgresql://localhost:5432/rumbo_db` | URL de conexión |
| `rumbo.jwt.secret` | *(ver application.properties)* | Secreto JWT — **cambiar en producción** |
| `rumbo.jwt.expiration-ms` | `86400000` (24h) | Expiración del token |
