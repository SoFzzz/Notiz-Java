# ADR-002: Auditoría síncrona

## 1. Título y estado

- Título: Registrar la auditoría de forma síncrona dentro del mismo hilo del cambio de nota.
- Estado: Aceptado
- Fecha: 2026-11-10

## 2. Contexto

Este ADR es el punto de compromiso entre el driver 1 (trazabilidad: 100 % de cambios registrados) y el driver 3 (rendimiento en cierre: p95 <= 2 s con 300 profesores). Una auditoría más lenta suma latencia a cada escritura; una auditoría que puede perderse contradice el driver 1. Coordinación y rectoría preguntan "¿qué pasa si una nota cambia y nadie sabe quién fue?".

## 3. Decisión

`AuditoriaListener` es un `@EventListener` síncrono de `NotaModificada`: escribe un `RegistroAuditoria` (1 insert) en el mismo hilo, antes de responder. El repositorio es append-only (no expone update ni delete) y `GET /api/auditoria` solo lee. La notificación, en cambio, es asíncrona (ADR-003).

## 4. Alternativas consideradas (descartadas)

| Alternativa | Por qué se descartó |
|---|---|
| Auditoría asíncrona (`@Async`) | Mejoraría la latencia, pero un fallo o caída después de guardar la nota dejaría un cambio sin registro. Se prioriza el driver 1 porque un cambio de nota sin evidencia es inaceptable para el negocio. |
| Auditar dentro de `NotaService` (sin evento) | Acopla calificaciones con auditoría y rompe la regla de que el evento es el único contrato. |
| Event sourcing | Excede plazo y capacidad del equipo para la necesidad (solo se requiere un registro de cambios). |

## 5. Consecuencias

Positivas:
- Si la respuesta es exitosa, el registro de auditoría ya existe (salvo R1).
- Modelo simple; la prueba demuestra el escenario 1 directamente.
- La auditoría sigue desacoplada por el evento.

Negativas:
- Cada escritura cuesta un insert más: latencia mayor (aceptable si el insert es corto; por medir en R2).
- Si el listener falla, la excepción puede propagarse a quien guarda la nota.
- **Sin transacción entre nota y auditoría** (MongoDB standalone): la nota puede quedar guardada sin registro si la auditoría falla después. Es el riesgo R1, no resuelto en el esqueleto.
- En la aprobación de una solicitud el orden es: guardar nota, guardar solicitud como APROBADA, publicar el evento (la auditoría síncrona corre dentro de `publishEvent`). Si la auditoría falla, nota y solicitud ya están guardadas y reintentar devuelve 400 "La solicitud ya fue resuelta": el registro de auditoría no se puede recuperar por la API (ver R1).
- La auditoría se vuelve parte de la ruta crítica: su lentitud degrada el cierre.
