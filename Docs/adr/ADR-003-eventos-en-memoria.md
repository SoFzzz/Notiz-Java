# ADR-003: Eventos en memoria

## 1. Título y estado

- Título: Comunicar contextos con eventos en memoria (`ApplicationEventPublisher`) en lugar de un broker.
- Estado: Aceptado
- Fecha: 2026-11-10

## 2. Contexto

Driver 2 (disponibilidad): el último día de cierre, si falla la notificación, el ingreso de notas debe seguir funcionando (secretaría académica). También se necesita que el evento sea el único contrato entre contextos (`03-estilo-y-contextos.md`). Restricciones: equipo de 3, ~2 semanas, un solo proceso, esqueleto donde la notificación solo hace log.

## 3. Decisión

`NotaService` publica `NotaModificada(notaId, valorAnterior, valorNuevo, autor, motivo, fecha)` con `ApplicationEventPublisher`. `AuditoriaListener` reacciona síncrono (ADR-002); `NotificacionListener` es `@Async @EventListener`. La propiedad `notiz.notificaciones.simular-fallo` hace que la notificación lance una excepción para demostrar que el ingreso de notas continúa.

## 4. Alternativas consideradas (descartadas)

| Alternativa | Por qué se descartó |
|---|---|
| RabbitMQ | Un contenedor y una pieza operativa más, con configuración de colas y reintentos; para un solo proceso en 2 semanas no se justifica. Daría durabilidad que hoy el esqueleto no necesita demostrar. |
| Kafka | Pensado para volúmenes y retención muy superiores; costo operativo desproporcionado. |
| Llamada directa a notificaciones desde `NotaService` | Acopla los contextos y un fallo de notificación subiría al flujo de la nota (viola el driver 2). |

## 5. Consecuencias

Positivas:
- Aislamiento de excepciones: la notificación puede fallar sin afectar el ingreso de notas (escenario 2).
- Cero infraestructura adicional; contextos desacoplados.
- Fácil de probar en el esqueleto.

Negativas:
- **Los eventos se pierden si el proceso cae**: no hay persistencia, reintento ni outbox. Una notificación fallida no se reenvía (R3).
- `@Async` aísla excepciones, no caídas del proceso.
- Sin orden ni entrega garantizados; difícil de observar (solo logs).
- Migrar a un broker después exige tocar el publicador.
