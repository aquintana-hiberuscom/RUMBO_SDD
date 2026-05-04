# US-008: Módulo de desempate automático en el ranking

## Story

**As a** administrador de la plataforma,
**I want** que el sistema detecte empates en el ranking al cierre del período y lance automáticamente un módulo de desempate a los usuarios afectados,
**so that** pueda obtenerse una clasificación final única y definitiva sin intervención manual.

## Context

Cuando varios usuarios tienen la misma puntuación (mismos aciertos y mismo tiempo) al cierre del
período del MVP, el sistema debe resolver el empate automáticamente. El mecanismo es un módulo
especial compuesto exclusivamente por preguntas del nivel más alto de dificultad disponible.
El resultado de este desempate determina la clasificación final de forma definitiva.

## Priority (MoSCoW)

- [ ] Must Have
- [x] Should Have — necesario para cerrar el ciclo gamificado, pero puede entregarse en un segundo sprint
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Requerimiento explícito del documento funcional (sección 4.3). Necesario para la integridad del sistema de ranking final.

## Acceptance Criteria

### AC-01: Desempate detectado y lanzado automáticamente al cierre del período

- **GIVEN** el período definido para el MVP ha finalizado y dos o más usuarios tienen exactamente la misma puntuación en el ranking
- **WHEN** el sistema evalúa el ranking final
- **THEN** activa automáticamente el módulo de desempate para todos los usuarios empatados
- **AND** cada usuario afectado recibe una notificación en la plataforma indicando que debe completar el desempate

### AC-02: El módulo de desempate contiene exclusivamente preguntas de máxima dificultad

- **GIVEN** el módulo de desempate ha sido activado para un usuario
- **WHEN** el usuario accede al módulo
- **THEN** únicamente encuentra preguntas del nivel más alto de dificultad disponible en el sistema
- **AND** el tiempo de respuesta de cada pregunta queda registrado con precisión

### AC-03: Clasificación final actualizada tras el desempate

- **GIVEN** todos los usuarios empatados han completado el módulo de desempate
- **WHEN** el sistema procesa los resultados
- **THEN** actualiza el ranking con la clasificación definitiva basada en el resultado del desempate (aciertos y tiempo dentro del módulo de desempate)
- **AND** el ranking final queda bloqueado — no puede ser modificado ni revertido

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la detección de empates y la activación del módulo
- [ ] Test de integración cubre el flujo completo: empate detectado → módulo activado → ranking actualizado
- [ ] El módulo de desempate no puede ser activado manualmente por ningún usuario
- [ ] Sin regresiones en el ranking ni en el sistema de módulos

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Automatización | El proceso de detección y lanzamiento es completamente automático — sin intervención del administrador |
| Integridad | El resultado del desempate es inmutable una vez procesado |
| Trazabilidad | El sistema registra quiénes estaban empatados, cuándo se lanzó el desempate y el resultado final |
| Performance | El procesamiento del resultado del desempate y actualización del ranking < 2s |

## Scope Boundaries

**In scope**:
- Detección automática de empates al cierre del período
- Activación del módulo de desempate con preguntas de máxima dificultad
- Registro de tiempo y aciertos del desempate
- Actualización definitiva e inmutable del ranking final

**Out of scope**:
- Desempate por criterios subjetivos o manuales
- Múltiples rondas de desempate (una sola ronda en el MVP)
- Desempate en rankings intermedios (solo aplica al ranking final del período)

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-007 Ver ranking global | Blocking | El desempate opera sobre el estado final del ranking |
| US-009 Identificación de ganadores | Informational | El resultado del desempate alimenta la identificación de ganadores |

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
