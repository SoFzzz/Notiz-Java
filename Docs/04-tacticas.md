# 04 - Tácticas

> Cada táctica nombra el escenario que la motiva y lo que degrada.

| # | Táctica | Categoría | Escenario / hoja | Dónde está | Qué degrada |
|---|---|---|---|---|---|
| T1 | Auditar (registro append-only de cada cambio) | Seguridad: detectar ataques / no repudio | Esc. 1 (trazabilidad) | `AuditoriaListener`, `RegistroAuditoria`, repositorio sin update/delete | Latencia de cada escritura: se suma un insert síncrono (ver ADR-002). |
| T2 | Desacoplar con eventos + aislar fallas con asincronía | Disponibilidad: prevención de fallas / aislamiento | Esc. 2 (disponibilidad) | `NotaModificada` + `NotificacionListener` (`@Async`); la asincronía requiere `@EnableAsync` en `NotizApplication` (sin esa anotación el listener correría síncrono y su fallo llegaría a la petición; lo verifica `AsincroniaNotificacionTest`) | La notificación puede perderse: no hay reintento ni outbox en el esqueleto. Hay consistencia eventual en el aviso. |
| T3 | Validar entrada y reglas en la capa de negocio | Seguridad / integridad | Esc. 1 y 3 | `ReglasNota` (`periodo`, `exigirAbierto`, `validarEscala`), usada por `NotaService` y `SolicitudService`; si no se cumple, `ManejadorErrores` responde 400 (o 404 si no existe el periodo/escala) y en periodo cerrado se pide una solicitud de cambio | Un poco de CPU por solicitud y más rigidez: el profesor no puede corregir periodo cerrado sin aprobación. |
| T4 | Mantener corta la escritura de nota | Rendimiento: reducir demanda de recursos | Esc. 3 (cierre) | Una nota = 1 insert de nota + 1 insert de auditoría; sin llamadas de red entre módulos | Limita qué se puede hacer en la ruta crítica (nada de PDF ni correo ahí). |
| T5 | Índices en las consultas frecuentes | Rendimiento | Esc. 3 | Índices Mongo por `notaId` en auditoría y por estudiante/periodo en notas; no están declarados todavía en el esqueleto | Más costo en escritura y espacio; poco relevante a esta escala. |
| T6 | Separar la regla por dependencia de datos (capas verificadas) | Modificabilidad | Hoja "escala por institución" (A, M) | `Escala` como dato por institución; `ArquitecturaTest` | Es una táctica secundaria; no es driver. |

## Limitaciones

- **Rendimiento no está probado con carga en el esqueleto.** T4 y T5 son decisiones de diseño razonables, no resultados medidos. El cumplimiento de p95 <= 2 s con 300 profesores es una hipótesis hasta correr la prueba de R2.
- T1 y T4 están en tensión: auditar cuesta latencia y rendimiento pide quitarla. Se resolvió a favor de trazabilidad (ADR-002) porque el negocio no tolera cambios sin registro.
- T2 protege contra excepciones en la notificación, no contra la caída del proceso.
- Fuera del esqueleto: HTTPS y control de acceso por rol (hojas (A, B) y (A, M)); se programan, no cambian la arquitectura. Lista completa: Login/OAuth2/JWT (seguridad), foros, chat, horarios, reportes, boletines PDF, carga masiva, multitenancy real, SendGrid y Redis.
