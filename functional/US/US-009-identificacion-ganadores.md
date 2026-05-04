# US-009: Identificación y clasificación de ganadores

## Story

**As a** administrador de la plataforma,
**I want** que el sistema identifique a los ganadores del ranking al cierre del período y me permita exportar su información,
**so that** pueda gestionar externamente la entrega de recompensas sin necesidad de cruzar datos de forma manual.

## Context

El documento funcional indica que la naturaleza de la recompensa no es alcance técnico, pero sí lo es
la capacidad de identificar y clasificar a los ganadores. Esta US cubre exclusivamente la parte técnica:
quiénes son los primeros clasificados y cómo acceder a sus datos básicos de identificación (nombre y email).
La gestión y entrega del premio es responsabilidad operativa del banco, fuera del sistema.

## Priority (MoSCoW)

- [ ] Must Have
- [ ] Should Have
- [x] Could Have — valioso pero no bloquea el MVP core
- [ ] Won't Have (this iteration)

**Rationale**: El MVP puede funcionar sin esta US en el primer sprint. La gestión de premios puede hacerse manualmente en la fase inicial.

## Acceptance Criteria

### AC-01: Ganadores identificados al cierre del período con datos de contacto

- **GIVEN** el período del MVP ha finalizado y el ranking (incluyendo desempate si aplicó) está cerrado
- **WHEN** el administrador accede al panel y consulta el ranking final
- **THEN** el sistema muestra los primeros clasificados con: posición, nombre completo, email y puntuación final
- **AND** los datos son los del momento de registro del usuario (no alterables)

### AC-02: Exportación de datos de ganadores en formato estándar

- **GIVEN** el administrador necesita los datos de los ganadores para gestionar la entrega de recompensas
- **WHEN** solicita exportar la lista de ganadores desde el panel
- **THEN** el sistema genera un archivo descargable (CSV) con: posición, nombre, apellidos, email y puntuación
- **AND** el archivo queda disponible para descarga en el mismo panel de administración

### AC-03: Historial de períodos cerrados inmutable

- **GIVEN** el administrador necesita consultar ganadores de períodos anteriores
- **WHEN** accede al historial de períodos en el panel de administración
- **THEN** puede ver los rankings finales y ganadores de cada período cerrado
- **AND** los datos de períodos ya cerrados no pueden ser editados ni eliminados

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la generación del listado de ganadores
- [ ] Test de integración cubre la exportación a CSV con datos correctos
- [ ] El acceso a esta funcionalidad está restringido exclusivamente al rol administrador
- [ ] Sin regresiones en el ranking ni en el sistema de períodos

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Seguridad | Acceso exclusivo para administradores autenticados. Los datos de ganadores (email) no son visibles para otros usuarios |
| Privacidad | Exportación de datos cumple con RGPD — solo datos mínimos necesarios para la gestión del premio |
| Integridad | El archivo exportado refleja fielmente el estado del ranking final cerrado |
| Trazabilidad | El sistema registra quién descargó el listado de ganadores y cuándo |

## Scope Boundaries

**In scope**:
- Visualización de ganadores con datos de identificación en el panel admin
- Exportación a CSV del listado de ganadores
- Historial de períodos cerrados con sus rankings finales

**Out of scope**:
- Gestión, validación o entrega de premios (responsabilidad operativa del banco)
- Notificación automática a los ganadores (deferred al MVP+)
- Integración con sistemas externos de gestión de premios

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-007 Ver ranking global | Blocking | El ranking final debe existir para identificar ganadores |
| US-008 Módulo de desempate | Informational | El desempate puede modificar los primeros puestos del ranking |

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
