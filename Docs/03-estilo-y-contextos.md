# 03 - Estilo arquitectónico y contextos delimitados

## 1. Estilo elegido

**Monolito modular en capas, con eventos en memoria para efectos secundarios.**

- Capas: presentación (controllers) -> negocio (services) -> datos (repositorios). Las dependencias van en un solo sentido; lo verifica ArchUnit en `Notiz/src/test/java/com/notiz/ArquitecturaTest.java`.
- Módulos: un paquete por contexto (`com.notiz.calificaciones`, `com.notiz.auditoria`, `com.notiz.notificaciones`).
- Eventos: `ApplicationEventPublisher` de Spring. La auditoría reacciona de forma síncrona; la notificación, asíncrona (`@Async`, que solo funciona porque `NotizApplication` tiene `@EnableAsync`).
- Despliegue: UN proceso Spring Boot 4.0.6 (Java 21) + UN contenedor MongoDB 7, con `docker compose up --build`.

### Qué driver lo justifica

| Driver | Cómo lo atiende el estilo |
|---|---|
| 1 Trazabilidad | Auditoría escucha el evento `NotaModificada` y escribe en su propia colección; nadie más escribe ahí. |
| 2 Disponibilidad | La notificación está desacoplada por evento asíncrono: su fallo no sube a quien guarda la nota. |
| 3 Rendimiento | Una llamada en proceso (sin red entre módulos) mantiene corta la ruta de escritura. |
| Restricciones | Equipo de 3, ~2 semanas, dominan Spring Boot: un solo proceso es lo que se puede entregar y operar. |

### Qué sacrifica

- **Escalado y despliegue independientes**: no se puede escalar solo calificaciones en el cierre; se escala todo el proceso.
- **Aislamiento de fallos fuerte**: una caída del proceso tumba los tres módulos. La asincronía aísla excepciones, no caídas.
- **Base de datos única = punto único de falla** (ver R2 en `06-riesgos.md`).
- **Disciplina por convención**: las fronteras entre módulos las protege el compilador y ArchUnit, no la red; hay que mantener esos tests.
- Los eventos en memoria se pierden si el proceso cae (ADR-003).

## 2. Contextos delimitados

| Contexto | Paquete | Qué contiene | Dueño | Ritmo de cambio | Vocabulario propio |
|---|---|---|---|---|---|
| Calificaciones | `com.notiz.calificaciones` | `Nota`, `Periodo` (ABIERTO/CERRADO), `Escala` (min/max por institución), `SolicitudCambio` (PENDIENTE/APROBADA/RECHAZADA). `NotaController`, `SolicitudController`, `PeriodoController` (`GET /api/periodos`), `NotaService`, `SolicitudService`, `PeriodoService`, `ReglasNota` (reglas compartidas: `periodo`, `exigirAbierto`, `validarEscala`), `NotaDtos`, `ManejadorErrores` (traduce `ReglaNegocioException` y la validación de entrada a 400 y `NoEncontradoException` a 404) y `DatosIniciales` (seed de demo: escala `E1`, periodo `P1` cerrado, periodo `P2` abierto, nota `N1` = 2.8). | Coordinación académica | Alto: reglas por institución y por periodo cambian cada año. | Nota, periodo, escala, solicitud de cambio, justificación. |
| Auditoría | `com.notiz.auditoria` | `RegistroAuditoria`, `RegistroAuditoriaRepository`, `AuditoriaListener` (escribe), `AuditoriaService` (consulta) y `AuditoriaController` (`GET /api/auditoria`). | Rectoría / control interno | Bajo: el formato de un registro de evidencia casi no cambia. | Registro, autor, valor anterior, valor nuevo, motivo. |
| Notificaciones | `com.notiz.notificaciones` | `NotificacionListener` (`@Async`), `NotificacionesRecientes` (registro en memoria de las últimas 50 notificaciones, `ENVIADA` o `FALLIDA`, con el hilo que las procesó), `SimulacionFallo` (interruptor en caliente, inicializado desde `notiz.notificaciones.simular-fallo`) y `NotificacionController` (`GET /api/notificaciones`, `GET` y `PUT /api/notificaciones/simular-fallo`, este último solo como control de demostración). En el esqueleto no envía nada: escribe `[NOTIFICACION] ...` en el log y en el registro en memoria, que se pierde al reiniciar. | Secretaría académica / comunicaciones | Alto: textos, canales y plantillas cambian seguido. | Mensaje, destinatario, canal. |

**Señal que justifica cada frontera**: distinto dueño (quién pide cambios), distinto ritmo de cambio (auditoría estable vs. notificaciones volátiles) y vocabulario distinto (la "nota" de calificaciones es, para auditoría, un "valor anterior/nuevo"). Una cuarta señal: distinta tolerancia al fallo (auditoría no puede fallar en silencio; notificación sí puede).

### Reglas entre contextos

1. **Cada contexto es dueño de sus datos.** Auditoría nunca toca `calificaciones.datos`; ArchUnit lo verifica. El repositorio de auditoría es append-only (sin update ni delete).
2. **El evento es el único contrato.** `NotaModificada(notaId, valorAnterior, valorNuevo, autor, motivo, fecha)` es lo único que Auditoría y Notificaciones conocen de Calificaciones. Cambiar el evento es cambiar un contrato.
3. Calificaciones no importa clases de Auditoría ni de Notificaciones; solo publica.
4. El panel de demostración (`Notiz/src/main/resources/static/index.html`) es una página estática de la capa de presentación, servida por la misma aplicación: solo consume la API REST y no agrega contenedores ni dependencias entre módulos. `ArquitecturaTest` verifica que `notificaciones` solo conoce de `calificaciones` el evento `NotaModificada`.
5. Las reglas de periodo y escala viven en un único lugar, `ReglasNota`, que usan tanto `NotaService` como `SolicitudService`; `SolicitudService` no llama a `NotaService`, sino que usa `NotaRepository`, `SolicitudCambioRepository` y `ReglasNota`, y ambos servicios publican `NotaModificada`.
