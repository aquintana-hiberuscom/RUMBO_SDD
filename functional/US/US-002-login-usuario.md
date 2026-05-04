# US-002: Login de usuario registrado

## Story

**As a** usuario ya registrado en la plataforma,
**I want** iniciar sesión con mi email y contraseña,
**so that** pueda acceder a mi progreso y continuar mi itinerario de aprendizaje donde lo dejé.

## Context

La plataforma tiene un sistema de autenticación propio, completamente independiente del login bancario.
No se usa SSO ni credenciales del banco bajo ninguna circunstancia.
Un usuario puede llegar a la plataforma desde un enlace directo o desde la web del Observatorio de Bienestar, pero el login es siempre propio de RUMBO.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Sin autenticación no hay personalización ni tracking de progreso. Requisito de MVP.

## Acceptance Criteria

### AC-01: Login exitoso con credenciales válidas

- **GIVEN** un usuario registrado accede a la pantalla de login
- **WHEN** introduce su email y contraseña correctos y pulsa "Entrar"
- **THEN** el sistema le autentica y le redirige a su panel principal
- **AND** el sistema recupera y muestra su progreso guardado

### AC-02: Credenciales incorrectas

- **GIVEN** un usuario intenta iniciar sesión con email o contraseña incorrectos
- **WHEN** envía el formulario de login
- **THEN** el sistema muestra un mensaje de error genérico ("Email o contraseña incorrectos") sin indicar cuál de los dos campos es incorrecto
- **AND** el formulario se limpia el campo de contraseña pero conserva el email

### AC-03: Acceso desde enlace externo (Observatorio de Bienestar)

- **GIVEN** un usuario llega a la plataforma RUMBO mediante un enlace desde la web corporativa del banco
- **WHEN** es redirigido a la pantalla de login de RUMBO
- **THEN** la autenticación se realiza exclusivamente con el sistema propio de la plataforma
- **AND** no se utilizan ni solicitan credenciales bancarias, cookies de sesión bancaria ni mecanismos SSO

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la lógica de validación de credenciales
- [ ] Test de integración cubre el flujo completo de autenticación (AC-01)
- [ ] JWT emitido correctamente tras login exitoso
- [ ] Sin regresiones en endpoints protegidos

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Respuesta del endpoint de login < 300ms bajo carga normal |
| Security | Protección frente a ataques de fuerza bruta (rate limiting básico). Sin logs de contraseñas en claro. |
| Independencia | Sin integración con sistemas de autenticación del banco bajo ninguna circunstancia |
| Accesibilidad | Formulario navegable por teclado, compatible con gestores de contraseñas |

## Scope Boundaries

**In scope**:
- Formulario de login (email + contraseña)
- Autenticación con JWT
- Redirección al panel principal tras login exitoso
- Mensaje de error genérico ante credenciales incorrectas

**Out of scope**:
- Recuperación de contraseña (Could Have — US separada)
- Recordar sesión / "mantener sesión iniciada" (deferred)
- Login con Google / redes sociales
- SSO bancario (explícitamente excluido por el documento funcional)
- Bloqueo de cuenta tras múltiples intentos fallidos (deferred al MVP+)

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-001 Registro | Blocking | Debe existir una cuenta para poder hacer login |

## Estimation

**Story Points**: 3
**T-shirt Size**: S

## SDD Traceability

> Rellenar después de ejecutar el pipeline SDD.

| Artifact | Reference |
|----------|-----------|
| Proposal | — |
| Spec | — |
| Design | — |
| Tasks | — |

---
_Created: 2026-05-04 | Status: Draft_
