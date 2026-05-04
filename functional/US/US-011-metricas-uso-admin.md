# US-011: Consulta de métricas básicas de uso y participación

## Story

**As a** administrador de la plataforma,
**I want** consultar métricas básicas de uso y participación desde el panel de administración,
**so that** pueda evaluar el rendimiento del MVP, detectar puntos de abandono y tomar decisiones informadas sobre la evolución de la plataforma.

## Context

El documento funcional requiere un panel básico de administración con capacidad de consultar métricas
de uso y participación (sección 6.4). Las métricas del MVP son orientativas del estado de la plataforma,
no son analítica avanzada. Su objetivo es dar visibilidad al banco sobre la adopción y el progreso de
los usuarios para evaluar el éxito del MVP.

## Priority (MoSCoW)

- [ ] Must Have
- [x] Should Have — importante para la toma de decisiones post-MVP, pero no bloquea el funcionamiento
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Las métricas son esenciales para validar el MVP con el banco, pero la plataforma puede operar sin ellas en el sprint inicial.

## Acceptance Criteria

### AC-01: Dashboard con métricas básicas de adopción y progreso

- **GIVEN** un administrador autenticado accede al panel de métricas
- **WHEN** visualiza el dashboard principal
- **THEN** ve al menos las siguientes métricas: número total de usuarios registrados, número de módulos completados (total), tasa de finalización por nivel (% de usuarios que completaron cada nivel) y distribución de usuarios por perfil
- **AND** las métricas reflejan los datos actualizados del sistema en el momento de la consulta

### AC-02: Filtrado de métricas por rango de fechas

- **GIVEN** un administrador consulta el dashboard de métricas
- **WHEN** aplica un filtro de rango de fechas (fecha inicio y fecha fin)
- **THEN** todas las métricas del dashboard se actualizan mostrando únicamente los datos del período seleccionado
- **AND** el filtro se puede eliminar para volver a ver los datos globales acumulados

### AC-03: Métricas de participación en el ranking y gamificación

- **GIVEN** un administrador quiere evaluar la participación en la parte gamificada de la plataforma
- **WHEN** accede a la sección de métricas de ranking
- **THEN** puede ver: número de usuarios que han entrado al ranking, posición media de los usuarios activos y distribución de puntuaciones (ej. histograma simple)
- **AND** puede identificar si hay un grupo de usuarios muy por encima del resto (para valorar el desempate)

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren los cálculos de cada métrica
- [ ] Test de integración cubre el filtrado por fecha y la consistencia de los datos
- [ ] El acceso al dashboard está restringido exclusivamente al rol administrador
- [ ] Sin regresiones en el rendimiento de la plataforma al calcular métricas (queries optimizadas)

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Performance | Carga del dashboard de métricas < 3s incluso con 10.000 usuarios |
| Precisión | Las métricas deben ser consistentes con los datos reales (sin aproximaciones salvo que se indique) |
| Seguridad | Acceso exclusivo para administradores. Los datos individuales no son exportables desde esta sección |
| Escalabilidad | La arquitectura de métricas debe soportar nuevas métricas sin rediseño del dashboard |

## Scope Boundaries

**In scope**:
- Dashboard con métricas básicas: usuarios registrados, módulos completados, tasa de finalización, distribución por perfil
- Filtrado por rango de fechas
- Métricas de participación en el ranking (número de participantes, distribución de puntuaciones)

**Out of scope**:
- Analítica avanzada o herramientas de BI externas (deferred al MVP+)
- Métricas individuales por usuario (pueden implicar privacidad — revisar con RGPD en MVP+)
- Exportación de datos de métricas en el MVP
- Alertas automáticas basadas en umbrales de métricas

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-001 Registro | Informational | Las métricas de adopción se basan en el número de registros |
| US-005 Completar módulo | Informational | Las métricas de progreso se basan en los módulos completados |
| US-007 Ver ranking | Informational | Las métricas de gamificación se basan en los datos del ranking |

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
