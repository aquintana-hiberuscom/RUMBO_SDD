# US-001: Registro de nuevo usuario en la plataforma

## Story

**As a** visitante no registrado de la plataforma,
**I want** registrarme con mis datos básicos (email, nombre, apellidos y edad),
**so that** pueda acceder al contenido educativo y mi progreso quede guardado entre sesiones.

## Context

La plataforma RUMBO es accesible públicamente desde una URL, sin necesidad de ser cliente del banco.
Cualquier persona puede registrarse con datos básicos. No se contrastan con sistemas externos.
Los datos recogidos deben cumplir con el RGPD. No se solicita información financiera ni bancaria sensible.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Sin registro no hay plataforma. Es la puerta de entrada de todos los usuarios.

## Acceptance Criteria

### AC-01: Registro exitoso con datos válidos

- **GIVEN** un visitante accede a la plataforma por primera vez
- **WHEN** rellena el formulario con email con formato válido, nombre, apellidos y edad (número entero positivo)
- **THEN** se crea su cuenta en el sistema
- **AND** es redirigido al paso de selección de perfil de usuario

### AC-02: Email ya registrado en el sistema

- **GIVEN** un visitante intenta registrarse con un email que ya existe en la plataforma
- **WHEN** envía el formulario de registro
- **THEN** el sistema muestra un mensaje de error indicando que ese email ya está en uso
- **AND** el formulario conserva los datos introducidos para facilitar la corrección

### AC-03: Campos obligatorios incompletos o con formato inválido

- **GIVEN** un visitante intenta enviar el formulario de registro sin completar todos los campos obligatorios, o con formato de email inválido
- **WHEN** pulsa el botón de envío
- **THEN** el sistema resalta visualmente los campos con error y muestra mensajes descriptivos por campo
- **AND** no se crea ninguna cuenta hasta que todos los campos sean válidos

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren validaciones de formulario
- [ ] Test de integración cubre AC-01 (flujo completo hasta redirección)
- [ ] Contrato API actualizado si se añaden endpoints de registro
- [ ] Sin regresiones en funcionalidades que compartan autenticación

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Respuesta del endpoint de registro < 500ms bajo carga normal |
| Security | Contraseña almacenada con hash (bcrypt o similar). Sin datos bancarios. |
| Privacidad | Cumplimiento RGPD: consentimiento informado, datos mínimos necesarios |
| Accesibilidad | Formulario navegable por teclado, etiquetas ARIA correctas |

## Scope Boundaries

**In scope**:
- Formulario de registro con email, nombre, apellidos y edad
- Validación de formato de email
- Validación de campos obligatorios
- Redirección post-registro a selección de perfil

**Out of scope**:
- Verificación de email por enlace (no requerido en MVP)
- Contraste de datos con sistemas externos
- Registro mediante redes sociales o SSO bancario
- Recuperación de contraseña (US separada, Could Have)

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| Ninguna | — | US independiente, primera del flujo |

## Estimation

**Story Points**: 3
**T-shirt Size**: S

## SDD Traceability

| Artifact | Reference |
|----------|-----------|
| Proposal | — |
| Spec | — |
| Design | Hexagonal: `domain/model/User`, `application/port/in/RegisterUserUseCase`, `application/port/out/UserRepository`, `application/service/RegisterUserService`, `infrastructure/adapter/out/persistence/`, `infrastructure/adapter/in/web/AuthController`, `infrastructure/config/SecurityConfig` + `JwtTokenProvider` |
| Tasks | Completado — implementación US-001 del 2026-05-04 |

---
_Created: 2026-05-04 | Status: Done_
