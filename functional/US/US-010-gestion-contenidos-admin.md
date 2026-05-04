# US-010: Gestión de contenidos desde el panel de administración

## Story

**As a** administrador de la plataforma,
**I want** gestionar los módulos y contenidos educativos desde un panel de administración,
**so that** pueda actualizar, publicar o retirar material formativo sin necesitar intervención técnica.

## Context

El documento funcional requiere un panel básico de administración que permita la gestión de contenidos.
El administrador es un usuario interno del banco (no un usuario final de la plataforma). El panel permite
controlar qué módulos están disponibles para los usuarios, editar su contenido y gestionar su ciclo de
publicación. El progreso ya completado por usuarios no debe verse afectado por cambios en los módulos.

## Priority (MoSCoW)

- [x] Must Have — requerido para MVP / sprint actual
- [ ] Should Have
- [ ] Could Have
- [ ] Won't Have (this iteration)

**Rationale**: Sin gestión de contenidos el MVP es una plataforma estática que no puede evolucionar. Requerimiento explícito del documento (sección 6.4).

## Acceptance Criteria

### AC-01: Listado de módulos con estado de publicación visible

- **GIVEN** un administrador autenticado accede al panel de administración y navega a la sección de contenidos
- **WHEN** visualiza el listado de módulos
- **THEN** ve todos los módulos organizados por perfil y nivel
- **AND** cada módulo muestra su estado de publicación actual (publicado / borrador / despublicado)

### AC-02: Edición de un módulo y publicación de cambios

- **GIVEN** un administrador accede a la edición de un módulo existente
- **WHEN** modifica el contenido (textos, preguntas, actividades) y guarda los cambios
- **THEN** los cambios se reflejan inmediatamente en la plataforma para los usuarios que aún no han completado ese módulo
- **AND** el progreso de usuarios que ya completaron el módulo no se elimina ni reinicia

### AC-03: Despublicar un módulo sin afectar el progreso existente

- **GIVEN** un administrador decide retirar temporalmente un módulo de la plataforma
- **WHEN** cambia el estado del módulo a "despublicado"
- **THEN** el módulo deja de ser visible y accesible para los usuarios en sus itinerarios
- **AND** el progreso de usuarios que ya completaron o estaban en ese módulo se conserva íntegramente

## Definition of Done

- [ ] Todos los criterios de aceptación pasan (manual o automatizado)
- [ ] Tests unitarios cubren la lógica de publicación/despublicación
- [ ] Test de integración cubre la edición de módulo y su impacto en usuarios existentes
- [ ] El acceso al panel está restringido exclusivamente al rol administrador
- [ ] Sin regresiones en el itinerario de usuarios al despublicar módulos

## Non-Functional Requirements

| Aspect | Requirement |
|--------|-------------|
| Usabilidad | Panel intuitivo, sin necesidad de conocimientos técnicos para gestionar contenidos |
| Seguridad | Acceso exclusivo para administradores autenticados con rol específico |
| Trazabilidad | Registro de auditoría de cambios: quién modificó qué y cuándo |
| Escalabilidad | El panel debe soportar cientos de módulos sin degradación de rendimiento |

## Scope Boundaries

**In scope**:
- Listado de módulos con estados y organización por perfil/nivel
- Creación y edición de módulos (contenido, preguntas, actividades)
- Publicación, despublicación y modo borrador
- Gestión de contenidos transversales (comunes a todos los perfiles)

**Out of scope**:
- CMS avanzado con versiones/histórico de cambios (deferred al MVP+)
- Gestión de multimedia avanzada (vídeos embebidos, etc.) — depende del diseño de contenidos
- Flujo de aprobación de contenidos (revisión → aprobación → publicación) — deferred
- Importación/exportación masiva de módulos

## Dependencies

| Depends on | Type | Notes |
|------------|------|-------|
| US-005 Completar módulo | Informational | Los cambios en módulos publicados no deben afectar progreso existente |
| US-006 Ver itinerario | Informational | Los módulos despublicados deben desaparecer del itinerario de usuarios |

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
