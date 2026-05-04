# US-003: Selección de perfil de usuario

## Story

**As a** usuario recién registrado en la plataforma,
**I want** seleccionar el perfil que mejor describe mi momento vital y situación financiera,
**so that** la plataforma personalice mi itinerario de aprendizaje con contenidos relevantes para mí.

## Context

El documento funcional define 4 perfiles orientativos: Explorador Digital (18-23), Primera Nómina (23-30),
Emprendedor/Autónomo (25-40) y Constructor de Futuro (30-40). La edad es orientativa y no excluyente —
cualquier usuario puede elegir cualquier perfil. La selección se realiza una única vez tras el registro,
y condiciona el journey de aprendizaje asignado.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: La personalización por perfil es uno de los pilares del MVP según el documento funcional.

## Acceptance Criteria

### AC-01: Selección de perfil exitosa con redirección al test

- **GIVEN** un usuario recién registrado accede a la pantalla de selección de perfil por primera vez
- **WHEN** selecciona uno de los 4 perfiles disponibles y confirma su elección
- **THEN** el sistema asocia ese perfil a su cuenta de forma persistente
- **AND** es redirigido al test de idoneidad inicial correspondiente a ese perfil

### AC-02: Información suficiente para elegir con criterio

- **GIVEN** un usuario está en la pantalla de selección de perfil
- **WHEN** visualiza las opciones disponibles
- **THEN** cada perfil muestra: nombre, rango de edad orientativo, contexto vital, necesidades financieras típicas y enfoque formativo
- **AND** el usuario puede expandir o leer la descripción completa de cada perfil antes de seleccionar

### AC-03: La selección de perfil no es excluyente por edad

- **GIVEN** un usuario de cualquier edad (ej. 50 años) accede a la selección de perfil
- **WHEN** selecciona cualquiera de los 4 perfiles disponibles
- **THEN** el sistema acepta la selección sin validar ni rechazar en función de la edad del usuario
- **AND** el itinerario se construye según el perfil elegido, no según la edad registrada

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la lógica de asignación de perfil
- [ ] Test de integración cubre el flujo registro → selección de perfil → redirección al test
- [ ] Los 4 perfiles están correctamente configurados en el sistema
- [ ] Sin regresiones en el flujo de onboarding

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Usabilidad | La pantalla de selección debe ser visual e intuitiva (cards, imágenes o iconos por perfil) |
| Performance | Carga de pantalla < 1s |
| Escalabilidad | La arquitectura debe permitir añadir nuevos perfiles sin rediseño estructural |
| Accesibilidad | Selección operable por teclado y con lectores de pantalla |

## Scope Boundaries

**In scope**:
- Pantalla de selección con los 4 perfiles del MVP
- Descripción completa de cada perfil
- Asociación del perfil a la cuenta del usuario
- Redirección al test de idoneidad

**Out of scope**:
- Cambio de perfil una vez iniciado el itinerario (deferred al MVP+)
- Más de 4 perfiles en el MVP
- Recomendación automática de perfil basada en datos del usuario
- Subsegmentaciones adicionales dentro de cada perfil

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-001 Registro | Blocking | El usuario debe estar registrado para llegar a esta pantalla |
| US-004 Test inicial | Informational | Esta US precede directamente al test de idoneidad |

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
