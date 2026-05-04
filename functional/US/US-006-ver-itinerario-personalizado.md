# US-006: Ver itinerario de aprendizaje personalizado

## Story

**As a** usuario registrado con perfil y nivel asignados,
**I want** ver mi itinerario de aprendizaje completo con el estado actualizado de cada módulo,
**so that** pueda planificar mi avance, saber cuánto me queda y retomar desde donde lo dejé.

## Context

El itinerario es el mapa de aprendizaje del usuario. Está compuesto por los módulos de su perfil
organizados en niveles progresivos (básico → intermedio → avanzado) más los contenidos transversales
comunes a todos los perfiles. El estado de cada módulo (pendiente, en progreso, completado, no superado)
debe persistir entre sesiones. El itinerario es el panel principal desde el que el usuario navega.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Sin visibilidad del itinerario el usuario no puede orientarse ni avanzar de forma autónoma.

## Acceptance Criteria

### AC-01: Itinerario visible con estado actualizado de cada módulo

- **GIVEN** un usuario autenticado accede a su panel principal
- **WHEN** visualiza su itinerario
- **THEN** ve todos los módulos de su perfil organizados por nivel (básico, intermedio, avanzado)
- **AND** cada módulo muestra su estado actual: pendiente, en progreso, completado o no superado

### AC-02: Itinerarios diferenciados según el perfil del usuario

- **GIVEN** dos usuarios con perfiles distintos (ej. Explorador Digital y Constructor de Futuro) están registrados
- **WHEN** cada uno accede a su itinerario
- **THEN** cada usuario ve únicamente los módulos específicos de su perfil
- **AND** los contenidos transversales (educación financiera básica, psicología del dinero, etc.) aparecen en ambos itinerarios

### AC-03: Progreso recuperado al volver a la plataforma

- **GIVEN** un usuario que completó varios módulos cierra la sesión y regresa días después
- **WHEN** inicia sesión y accede a su itinerario
- **THEN** el sistema muestra exactamente el mismo estado de progreso que tenía al cerrar sesión
- **AND** el sistema sugiere visualmente el siguiente módulo pendiente de completar

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la lógica de construcción del itinerario por perfil
- [ ] Test de integración cubre la persistencia del progreso entre sesiones
- [ ] Los contenidos transversales aparecen en todos los perfiles
- [ ] Sin regresiones en la navegación a módulos individuales

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Carga del itinerario completo < 1s |
| Persistencia | El estado de progreso no se pierde bajo ninguna circunstancia (cambio de dispositivo, cierre de navegador) |
| Responsive | Itinerario usable en móvil con scroll vertical natural |
| Escalabilidad | La estructura soporta nuevos niveles o módulos sin rediseño del componente |

## Scope Boundaries

**In scope**:
- Vista del itinerario completo del perfil del usuario
- Estado por módulo (pendiente / en progreso / completado / no superado)
- Contenidos transversales comunes a todos los perfiles
- Sugerencia del siguiente módulo pendiente
- Navegación desde el itinerario a cada módulo

**Out of scope**:
- Cambio de perfil desde el itinerario (deferred)
- Vista comparativa con el itinerario de otro usuario
- Estimación de tiempo restante para completar el itinerario

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-003 Selección de perfil | Blocking | El perfil determina qué módulos forman el itinerario |
| US-004 Test de idoneidad | Blocking | El nivel inicial determina el punto de entrada en el itinerario |
| US-005 Completar módulo | Informational | Las acciones sobre módulos actualizan el estado en el itinerario |

## Estimation

**Story Points**: 5
**T-shirt Size**: M

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
