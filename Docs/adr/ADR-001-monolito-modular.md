# ADR-001: Monolito modular en capas

## 1. Título y estado

- Título: Adoptar un monolito modular en capas (un proceso Spring Boot + una base MongoDB).
- Estado: Aceptado
- Fecha: 2026-11-10

## 2. Contexto

Drivers: 3 (rendimiento en cierre, ruta de escritura corta), 2 (disponibilidad, aislar notificaciones) y 1 (trazabilidad, ver `01-drivers.md`). Restricciones: equipo de 3 personas, ~2 semanas, dominio de Java/Spring Boot. Hay tres contextos con dueños y ritmos de cambio distintos (calificaciones, auditoría, notificaciones; ver `03-estilo-y-contextos.md`), pero se comunican por un único evento.

## 3. Decisión

Un solo proceso Spring Boot (Java 21) con un paquete por contexto y capas presentación -> negocio -> datos, más un contenedor MongoDB 7. Las reglas de dependencia (un solo sentido; auditoría no toca `calificaciones.datos`) se verifican con ArchUnit en `ArquitecturaTest`. Se arranca con `docker compose up --build` (2 contenedores).

## 4. Alternativas consideradas (descartadas)

| Alternativa | Por qué se descartó |
|---|---|
| Microservicios (un servicio por contexto) | Añade red, despliegues, trazabilidad distribuida y consistencia entre servicios; con 3 personas y 2 semanas no es entregable. Además pondría una llamada de red en la ruta de guardar una nota (empeora el driver 3) y volvería la auditoría un problema de consistencia distribuida (driver 1). No hay hoy una señal de escalado independiente que lo pague. |
| Capas sin módulos (una sola estructura por capa técnica) | Más simple, pero las fronteras de contexto no quedarían visibles ni verificables; auditoría podría acabar leyendo datos de calificaciones y el camino a separar se cerraría. |

## 5. Consecuencias

Positivas:
- Entregable por el equipo; despliegue trivial.
- Llamadas en proceso: ruta de escritura corta.
- Fronteras explícitas y comprobadas por ArchUnit; se puede extraer un módulo después.

Negativas:
- No se escala ni se despliega cada contexto por separado.
- Un fallo del proceso o de MongoDB afecta a todo (SPOF; ver R2).
- Las fronteras dependen de disciplina y de mantener los tests de arquitectura.
- Todo el equipo comparte un mismo ciclo de despliegue.
