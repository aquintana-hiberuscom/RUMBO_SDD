# US-005: Completar un módulo de aprendizaje gamificado

## Story

**As a** usuario con un itinerario de aprendizaje asignado,
**I want** completar módulos de contenido financiero con dinámicas gamificadas (retos, preguntas, pruebas),
**so that** aprenda conceptos financieros de forma progresiva y motivadora, y mi avance quede registrado.

## Context

Los módulos son la unidad básica de aprendizaje. Cada módulo pertenece a un nivel (básico, intermedio,
avanzado) dentro del itinerario de un perfil. La superación de un módulo depende del porcentaje de
aciertos y/o la finalización de los retos que lo componen. Los módulos no superados pueden repetirse.
La gamificación es intrínseca al formato: no es una capa añadida, sino el vehículo del aprendizaje.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Es el núcleo funcional de la plataforma. Sin módulos de aprendizaje no hay producto.

## Acceptance Criteria

### AC-01: Módulo completado exitosamente — progreso y puntuación actualizados

- **GIVEN** un usuario accede a un módulo disponible en su itinerario
- **WHEN** completa todas las actividades del módulo y supera el umbral de aciertos requerido
- **THEN** el módulo queda marcado como "completado" en su itinerario
- **AND** su puntuación (basada en porcentaje de aciertos y tiempo de finalización) se actualiza en el ranking

### AC-02: Módulo no superado — posibilidad de reintentar

- **GIVEN** un usuario finaliza todas las actividades de un módulo pero no alcanza el umbral de aciertos
- **WHEN** el sistema evalúa el resultado
- **THEN** el módulo queda marcado como "no superado" y el usuario recibe feedback sobre su resultado
- **AND** puede volver a intentar el módulo desde el inicio sin penalización en puntuación previa

### AC-03: Acceso bloqueado a módulos sin prerrequisitos completados

- **GIVEN** un usuario tiene módulos de nivel básico o intermedio sin completar
- **WHEN** intenta acceder a un módulo de nivel superior que requiere prerrequisitos
- **THEN** el sistema bloquea el acceso al módulo superior
- **AND** muestra claramente qué módulos debe completar primero para desbloquear el acceso

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren el algoritmo de evaluación (cálculo de puntuación y superación)
- [ ] Test de integración cubre el flujo completo: acceder → completar → actualizar ranking
- [ ] El umbral de superación por módulo es configurable desde el panel de administración
- [ ] Sin regresiones en el itinerario ni en el ranking

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Carga de módulo < 2s. Guardado de respuesta por actividad en tiempo real (sin perder progreso si el usuario cierra el navegador) |
| Escalabilidad | La estructura de módulos debe soportar nuevos tipos de actividad sin rediseño |
| Responsive | Experiencia completa en desktop y móvil |
| Accesibilidad | Actividades jugables sin ratón (teclado). Contraste de colores WCAG AA |

## Scope Boundaries

**In scope**:
- Visualización y navegación por las actividades de un módulo
- Evaluación del resultado (porcentaje de aciertos)
- Marcado de módulo como completado o no superado
- Bloqueo de acceso a módulos sin prerrequisitos
- Actualización de puntuación en el ranking al completar

**Out of scope**:
- Tipos específicos de actividades gamificadas (diseño de contenidos — no es alcance técnico)
- Sistema de insignias o badges por módulo (deferred al MVP+)
- Modo competitivo en tiempo real con otros usuarios

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-004 Test de idoneidad | Blocking | El nivel inicial determina qué módulos están disponibles |
| US-006 Ver itinerario | Informational | El estado del módulo se refleja en el itinerario |
| US-007 Ver ranking | Informational | La puntuación al completar actualiza el ranking |

## Estimation

**Story Points**: 8
**T-shirt Size**: L

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
