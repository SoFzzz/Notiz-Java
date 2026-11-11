# 06 - Riesgos

## R1 - Nota guardada sin registro de auditoría (sin transacción en MongoDB standalone)

- **Descripción**: MongoDB corre standalone, sin replica set, así que no hay transacción que abarque guardar la nota y guardar la auditoría. Si la auditoría falla después de guardar la nota, queda un cambio sin registro.
- **Detalle del orden en `SolicitudService.aprobar`**: guarda la nota, guarda la solicitud como APROBADA y recién después publica `NotaModificada` (la auditoría corre síncrona dentro de `publishEvent`). Si la auditoría falla, la nota Y la solicitud ya están guardadas; al reintentar, la solicitud ya no está PENDIENTE y la API responde 400 "La solicitud ya fue resuelta". El registro de auditoría faltante no se puede recuperar por la API; habría que corregirlo directamente en la base. (`NotaService.crear/editar` tiene el mismo problema: guarda la nota y luego publica.)
- **Driver afectado**: 1 (trazabilidad), y ADR-002.
- **Probabilidad / impacto**: baja-media / alto.
- **Verificación prevista**: forzar un fallo en `AuditoriaListener` (por ejemplo, excepción en el insert) y revisar si la nota quedó modificada en la base. Si queda, el riesgo es real.
- **Mitigación**: MongoDB como replica set (de un nodo basta para transacciones) + `@Transactional`; o patrón outbox. Mientras tanto, mantenerlo documentado.

## R2 - Una sola instancia de MongoDB y un solo proceso en el cierre a 10x

- **Descripción**: la app y MongoDB son instancias únicas. En el cierre, si la carga crece 10x o la base se cae, el ingreso de notas se degrada o se detiene.
- **Driver afectado**: 3 (rendimiento) y, por la caída, 2 (disponibilidad).
- **Probabilidad / impacto**: media / alto.
- **Verificación prevista**: prueba de carga con k6 o JMeter sobre `POST /api/notas` con 300 -> 3000 profesores concurrentes; medir p95 y tasa de errores frente a p95 <= 2 s. Observar CPU del proceso y de MongoDB para ver quién satura primero. Hasta correr esta prueba, el escenario 3 no está demostrado.
- **Mitigación**: replica set; varias instancias de la app detrás de un balanceador; revisar índices (T5); si el cuello es el hilo de la auditoría síncrona, reevaluar ADR-002 con outbox.

## R3 (opcional) - Notificaciones perdidas

- **Descripción**: eventos en memoria y `@Async` sin reintento: si el proceso cae o la notificación falla, el aviso no se reenvía.
- **Driver afectado**: 2 (acotado: no afecta el ingreso de notas, pero sí el aviso).
- **Probabilidad / impacto**: media / bajo-medio.
- **Verificación prevista**: con `notiz.notificaciones.simular-fallo=true`, confirmar que no hay reenvío; matar el proceso tras aprobar y revisar si el aviso llegó.
- **Mitigación**: outbox con reintentos y un broker (ADR-003); registrar los avisos fallidos para reenvío manual.
