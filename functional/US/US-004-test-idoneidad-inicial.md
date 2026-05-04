# US-004: Test de idoneidad inicial de conocimientos

## Story

**As a** usuario que acaba de seleccionar su perfil en la plataforma,
**I want** realizar un test de conocimientos financieros adaptado a mi perfil,
**so that** la plataforma determine mi nivel de entrada y evite que repita contenido que ya domino.

## Context

El test de idoneidad es el mecanismo de personalización del itinerario. Permite que un usuario con
conocimientos previos salte niveles básicos y empiece desde donde realmente le aporta valor.
El nivel asignado (básico, intermedio o avanzado) determina el punto de inicio del itinerario de
aprendizaje. El umbral de puntuación para saltar niveles queda a criterio del diseño de contenidos,
pero debe ser configurable.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Requisito explícito del documento funcional (sección 3.2). Permite la personalización real del recorrido.

## Acceptance Criteria

### AC-01: Test completado — nivel asignado y redirección al itinerario

- **GIVEN** un usuario autenticado con perfil asignado accede al test de idoneidad
- **WHEN** completa todas las preguntas del test y lo envía
- **THEN** el sistema calcula su puntuación y le asigna el nivel inicial correspondiente (básico, intermedio o avanzado)
- **AND** es redirigido al primer módulo pendiente del nivel asignado en su itinerario

### AC-02: Puntuación alta — el usuario salta niveles ya dominados

- **GIVEN** un usuario completa el test con un porcentaje de aciertos que supera el umbral configurado para un nivel
- **WHEN** el sistema procesa los resultados
- **THEN** los niveles ya dominados quedan marcados como "superados" en su itinerario
- **AND** el usuario comienza directamente en el nivel superior al que demostró dominar

### AC-03: Puntuación insuficiente — el usuario empieza desde el nivel básico

- **GIVEN** un usuario completa el test sin superar el umbral mínimo de ningún nivel
- **WHEN** el sistema procesa los resultados
- **THEN** el usuario es asignado al nivel básico del itinerario de su perfil
- **AND** puede comenzar el itinerario desde el principio sin ninguna penalización en su puntuación de ranking

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren el algoritmo de cálculo de nivel inicial
- [ ] Test de integración cubre el flujo completo: test → asignación de nivel → redirección
- [ ] El umbral de puntuación por nivel es configurable sin cambios de código
- [ ] Sin regresiones en el acceso al itinerario de aprendizaje

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Configurabilidad | Umbral de puntuación para cada nivel configurable desde el panel de administración |
| Performance | Cálculo del nivel y redirección en < 500ms tras enviar el test |
| UX | Mostrar resultado del test (puntuación y nivel asignado) antes de redirigir, con feedback positivo |
| Escalabilidad | El banco de preguntas debe ser ampliable sin modificar la lógica de evaluación |

## Scope Boundaries

**In scope**:
- Test de preguntas de conocimientos financieros adaptado al perfil seleccionado
- Algoritmo de asignación de nivel inicial basado en puntuación
- Posibilidad de saltar uno o más niveles si la puntuación lo justifica
- Redirección al itinerario con el nivel correcto

**Out of scope**:
- Repetición del test de idoneidad (sólo se realiza una vez, al inicio)
- Test de idoneidad por sub-área temática (deferred al MVP+)
- Banco de preguntas adaptativo en tiempo real durante el propio test

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-003 Selección de perfil | Blocking | El test es específico del perfil seleccionado |
| US-006 Ver itinerario | Informational | El resultado del test afecta el estado inicial del itinerario |

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
