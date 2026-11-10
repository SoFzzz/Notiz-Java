# 02 - Escenarios de calidad

## Escenario 1 - Trazabilidad de cambios de nota (driver 1, Seguridad)

| Parte | Contenido |
|---|---|
| Fuente | Un administrador (coordinación) que aprueba un cambio de nota. |
| Estímulo | Aprueba una solicitud de cambio sobre una nota de periodo cerrado (valor anterior 2.8, nuevo 4.0, como en la demo con la nota semilla `N1`). |
| Artefacto | `SolicitudService` (calificaciones; usa `NotaRepository`, `SolicitudCambioRepository` y `ReglasNota`, y publica `NotaModificada`) y `AuditoriaListener` (auditoría). `NotaService` publica el mismo evento en las ediciones directas. |
| Entorno | Operación normal, un solo proceso, MongoDB disponible. |
| Respuesta | `SolicitudService` guarda la nota y la solicitud como APROBADA, publica `NotaModificada` y el listener síncrono guarda un `RegistroAuditoria` con notaId, valor anterior, valor nuevo, autor, motivo y fecha. El repositorio de auditoría no expone update ni delete. |
| Medida | 100 % de los cambios con registro completo; 0 operaciones de edición o borrado de registros en la API. |

Cómo se demuestra en el esqueleto: paso 6 de `demo.sh` / `demo.ps1`: `GET /api/auditoria?notaId=N1` muestra valor anterior y nuevo. Además, `ArquitecturaTest` verifica que auditoría no depende de `calificaciones.datos`.

## Escenario 2 - Ingreso de notas sigue vivo si falla la notificación (driver 2, Disponibilidad)

| Parte | Contenido |
|---|---|
| Fuente | Un administrador que aprueba una solicitud, o un profesor que guarda una nota en periodo abierto; fuente del fallo: el servicio de notificación (correo). |
| Estímulo | Se aplica un cambio de nota (aprobación de solicitud o guardado de nota en periodo abierto) y el servicio de notificación falla (`NotificacionListener` lanza una excepción). |
| Artefacto | `NotificacionListener` (`@Async @EventListener`) y `NotaService`. |
| Entorno | Último día de cierre de periodo, carga alta. |
| Respuesta | La petición HTTP responde con éxito, la nota queda guardada y la auditoría se escribe; el fallo solo queda registrado en el log del hilo asíncrono (requiere `@EnableAsync` en `NotizApplication`). |
| Medida | 100 % de peticiones de cambio exitosas con la notificación fallando; 0 respuestas 5xx causadas por la notificación. |

Cómo se demuestra: arrancar con `notiz.notificaciones.simular-fallo=true`, repetir el paso 5 de la demo (aprobar): la respuesta es exitosa, la consola muestra el error del listener y la auditoría (paso 6) tiene el registro. Se recomienda un test de integración equivalente.

## Escenario 3 - Guardar una nota en el cierre de periodo (driver 3, Rendimiento)

| Parte | Contenido |
|---|---|
| Fuente | 300 profesores concurrentes. |
| Estímulo | Cada uno guarda notas (`POST /api/notas`) durante el cierre. |
| Artefacto | `NotaController` -> `NotaService` -> repositorio Mongo de notas + 1 insert de auditoría. |
| Entorno | Día de cierre, un proceso Spring Boot y un MongoDB. |
| Respuesta | Validación de regla (periodo abierto y escala), 1 escritura de nota, 1 escritura de auditoría, respuesta 201. |
| Medida | p95 <= 2 s y 0 % de errores. |

Cómo se demuestra: en el esqueleto la ruta es corta (2 escrituras) pero **no hay prueba de carga**. Se deja como verificación en `06-riesgos.md` (R2): script k6 o JMeter contra `POST /api/notas`. No afirmar el cumplimiento sin esa medición.
