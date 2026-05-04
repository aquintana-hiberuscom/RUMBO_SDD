# US-007: Ver ranking global de usuarios

## Story

**As a** usuario registrado en la plataforma,
**I want** ver mi posición en el ranking global y la clasificación de los demás participantes,
**so that** pueda compararme con otros usuarios, medir mi progreso competitivo y motivarme a avanzar.

## Context

El ranking global clasifica a todos los usuarios según su rendimiento en la plataforma.
Los criterios de clasificación según el documento funcional son: tiempo de finalización del itinerario
y porcentaje de aciertos. El ranking es visible dentro de la plataforma para todos los usuarios
autenticados. La posición propia debe destacarse visualmente para facilitar la referencia rápida.

## Priority (MoSCoW)

- [ ] Must Have
- [x] Should Have — importante, pero puede diferirse un sprint si hay presión de tiempo
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: El ranking es el motor de motivación competitiva del MVP. Es importante pero el aprendizaje puede funcionar sin él en un sprint inicial.

## Acceptance Criteria

### AC-01: Ranking visible con posición propia destacada

- **GIVEN** un usuario autenticado accede a la sección de ranking
- **WHEN** visualiza la tabla de clasificación
- **THEN** ve a todos los participantes ordenados de mayor a menor puntuación (combinación de aciertos y tiempo)
- **AND** su propia posición queda destacada visualmente (color diferenciado, indicador "Tú") aunque no esté en el top de la lista

### AC-02: Criterios de puntuación transparentes y visibles

- **GIVEN** un usuario consulta el ranking
- **WHEN** quiere entender cómo se calcula su posición
- **THEN** la interfaz muestra claramente los criterios de puntuación: porcentaje de aciertos acumulado y tiempo total de finalización
- **AND** cada entrada del ranking muestra ambos valores junto a la posición y nombre del usuario

### AC-03: Ranking actualizado tras completar un módulo

- **GIVEN** un usuario acaba de completar un módulo con una puntuación que mejora su posición
- **WHEN** accede al ranking
- **THEN** su nueva posición refleja el resultado recién obtenido
- **AND** el cambio de posición es visible en la misma sesión sin necesidad de recargar la página

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren el algoritmo de cálculo y ordenación del ranking
- [ ] Test de integración cubre la actualización del ranking tras completar un módulo
- [ ] El rendimiento del ranking no degrada con 1000+ usuarios
- [ ] Sin regresiones en la actualización de puntuación al completar módulos

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Carga del ranking < 1s. Actualización en tiempo real o near-real-time (< 5s de lag) |
| Privacidad | Solo se muestra nombre del usuario (no email ni otros datos personales) en el ranking público |
| Escalabilidad | El ranking debe funcionar correctamente con hasta 10.000 usuarios simultáneos en MVP |
| Responsive | Tabla de ranking usable en móvil (scroll horizontal o diseño adaptado) |

## Scope Boundaries

**In scope**:
- Tabla de ranking global con todos los participantes
- Criterios visibles: aciertos y tiempo
- Destacado de la posición propia
- Actualización al completar módulos

**Out of scope**:
- Rankings por perfil o segmento (deferred al MVP+)
- Rankings por período de tiempo (deferred)
- Notificaciones push cuando el usuario sube de posición
- Historial de evolución de posición del usuario

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-005 Completar módulo | Blocking | El ranking se alimenta de los resultados de los módulos completados |

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
