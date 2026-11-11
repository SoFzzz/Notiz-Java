# Documentación de arquitectura - Notiz

Proyecto integrador, Arquitectura de Software, UCC Pasto. Stack: Spring Boot 4.0.6, Java 21, MongoDB 7.

## Índice

| Archivo | Contenido |
|---|---|
| `01-drivers.md` | Restricciones, funciones primarias, árbol de utilidad con marcas y los 3 drivers. |
| `02-escenarios.md` | 3 escenarios de calidad completos (fuente, estímulo, artefacto, entorno, respuesta, medida). |
| `03-estilo-y-contextos.md` | Monolito modular en capas + eventos, lo que sacrifica, y contextos delimitados. |
| `04-tacticas.md` | Tácticas, escenario que atienden y qué degradan. |
| `05-c4.md` | C4 niveles 1 y 2 (y 3 opcional) en Mermaid, con leyenda. |
| `c4/workspace.dsl` | Los mismos diagramas en Structurizr DSL. |
| `adr/ADR-001-monolito-modular.md` | Monolito modular vs microservicios y capas sin módulos. |
| `adr/ADR-002-auditoria-sincrona.md` | Auditoría síncrona vs asíncrona. |
| `adr/ADR-003-eventos-en-memoria.md` | Eventos en memoria vs broker. |
| `06-riesgos.md` | Riesgos y verificación prevista. |

## Matriz de trazabilidad

| Decisión | Driver / hoja | Evidencia en código |
|---|---|---|
| Monolito modular en capas (ADR-001) | Rendimiento en cierre; restricción equipo/plazo | `ArquitecturaTest` (`Notiz/src/test/java/com/notiz/ArquitecturaTest.java`); `docker-compose.yml` (2 contenedores) |
| Auditoría síncrona y append-only (ADR-002, T1) | Trazabilidad (Seguridad, A, A) | `AuditoriaListener`, `AuditoriaService`, `RegistroAuditoriaRepository` sin update/delete, `GET /api/auditoria`; `SolicitudServiceTest` (aprobar publica el evento); paso 6 de la demo |
| Eventos en memoria + notificación asíncrona (ADR-003, T2) | Disponibilidad (A, A) | `NotaModificada`, `NotificacionListener` (`@Async`), `@EnableAsync` en `NotizApplication` (`AsincroniaNotificacionTest`), propiedad `notiz.notificaciones.simular-fallo` y `PUT /api/notificaciones/simular-fallo`; `NotificacionListenerTest` (FALLIDA y excepción); panel de demostración (`Notiz/src/main/resources/static/index.html`): con el fallo activo el paso 5 sigue en 200 y la notificación queda FALLIDA |
| Validar reglas en negocio (T3) | Trazabilidad / integridad; rendimiento | `ReglasNota` (periodo ABIERTO, valor dentro de `Escala`) usada por `NotaService` y `SolicitudService`; `NotaServiceTest` y `SolicitudServiceTest`; pasos 2 y 3 de la demo |
| Ruta de escritura corta (T4) y índices (T5) | Rendimiento en cierre (A, A) | `NotaService` (1 insert de nota + 1 de auditoría; `NotaServiceTest` verifica el evento); **sin prueba de carga: ver R2** |
| Contextos y datos propios | Modificabilidad; trazabilidad | Paquetes `com.notiz.calificaciones`, `auditoria`, `notificaciones`; `ArquitecturaTest` (auditoría y notificaciones no tocan `calificaciones.datos`; no se dependen entre sí; notificaciones solo conoce el evento `NotaModificada`) |
| Chat fuera del esqueleto | (B, A): se negocia | No hay código de chat |
