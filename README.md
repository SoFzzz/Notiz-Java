# Notiz

Notiz es una plataforma web de gestión académica institucional: los profesores registran calificaciones, los administradores controlan los cambios sobre periodos cerrados y cada modificación queda auditada. Proyecto integrador de Ingeniería de Software, Universidad Cooperativa de Colombia (UCC).

- Integrantes: Paula Sofía González, David Alejandro Aza, Stehven Alexander Obando

## Alcance del walking skeleton

Este repositorio implementa **un solo flujo de punta a punta**:

> solicitud de cambio de nota -> aprobación del administrador -> evento `NotaModificada` -> auditoría síncrona + notificación asíncrona

Reglas incluidas: una nota solo se edita directamente si su periodo está ABIERTO y el valor está dentro de la escala; en un periodo CERRADO hay que pedir un cambio, que un administrador aprueba o rechaza. Al aprobar, la nota cambia, se guarda un registro de auditoría (si falla, la petición falla) y se envía una notificación (si falla, la nota igual queda guardada).

No hay login: el actor (profesor o administrador) llega en el cuerpo de la petición. Los módulos fuera de alcance (login/seguridad, foros, chat, horarios, reportes, boletines PDF, carga masiva, multitenancy real, SendGrid y Redis) viven en el tag de git `andamiaje-original`.

## Requisitos

- Docker Desktop (para levantar MongoDB y la aplicación)
- Para correr las pruebas sin Docker: JDK 21 (se usa el wrapper de Maven incluido)

## Ejecutar

```bash
docker compose up --build
```

La API queda en `http://localhost:8080` y MongoDB en `localhost:27017`. Al arrancar se cargan datos de demostración (escala 1.0-5.0, periodo `P1` cerrado, periodo `P2` abierto y la nota `N1` en `P1`).

### Demo del flujo

Con la aplicación arriba, en otra terminal:

```bash
./demo.sh      # Linux, macOS, Git Bash (usa jq si está instalado)
./demo.ps1     # Windows PowerShell
```

El script recorre 6 pasos: nota válida (201), nota fuera de escala (400), nota en periodo cerrado (400), solicitud de cambio (PENDIENTE), aprobación (la consola muestra `[NOTIFICACION]`) y consulta de auditoría con `valorAnterior` 2.8 y el valor nuevo.

### Panel de demostración

Con la aplicación arriba (`docker compose up --build`), abra **http://localhost:8080**. Es una página estática servida por la misma aplicación que solo llama a la API REST; no hay contenedor ni build aparte.

- **Pasos guiados (1 a 6)**: los mismos de `demo.ps1`. Cada paso muestra una tarjeta con el código HTTP (verde o rojo), el mensaje y el JSON crudo desplegable. Se pueden repetir: el paso 5 aprueba la última solicitud PENDIENTE de `N1` (si no hay, pide hacer el paso 4 primero). El botón "Rechazar" resuelve esa misma solicitud sin cambiar la nota.
- **Tablas en vivo** (se refrescan cada 2 s y tras cada acción; las filas nuevas se resaltan): notas, solicitudes, auditoría (síncrona, se guarda dentro de la misma petición) y notificaciones (asíncrona; la columna *hilo* muestra `task-N`, un hilo distinto al de la petición).
- **Simular fallo de notificación**: interruptor que llama a `PUT /api/notificaciones/simular-fallo`. Con el fallo activo aparece un aviso en la parte superior; al aprobar una solicitud (paso 5) la respuesta sigue siendo 200, la nota y la auditoría se guardan y la notificación queda FALLIDA. Es un control de demostración, no parte del dominio.
- Un formulario plegable permite crear una nota con valores propios.

### Simular una falla de notificación

```bash
NOTIZ_NOTIFICACIONES_SIMULARFALLO=true docker compose up --build
```

(En PowerShell: `$env:NOTIZ_NOTIFICACIONES_SIMULARFALLO="true"; docker compose up --build`.) La notificación registra `[NOTIFICACION] fallo simulado` y lanza una excepción, pero la nota se guarda y la auditoría se registra igual. La propiedad solo fija el valor inicial: en caliente se cambia desde el panel o con `PUT /api/notificaciones/simular-fallo`.

## Pruebas

No requieren MongoDB ni Docker.

```bash
cd Notiz
./mvnw test          # Windows: mvnw.cmd test
```

Incluyen pruebas unitarias de los servicios (Mockito) y reglas de arquitectura con ArchUnit.

## Endpoints

| Método | Ruta | Descripción |
|--------|------|-------------|
| POST | `/api/notas` | Crea una nota `{estudianteId, materia, periodoId, valor, profesor}` (201) |
| PUT | `/api/notas/{id}` | Edita una nota de un periodo abierto `{valor, profesor}` |
| GET | `/api/notas[?periodoId=]` | Lista notas |
| GET | `/api/notas/{id}` | Consulta una nota |
| POST | `/api/notas/{id}/solicitudes` | Solicita un cambio `{valorPropuesto, justificacion, profesor}` (201, PENDIENTE) |
| GET | `/api/solicitudes[?estado=PENDIENTE]` | Lista solicitudes |
| POST | `/api/solicitudes/{id}/aprobar` | Aprueba `{administrador}`: aplica el cambio y publica el evento |
| POST | `/api/solicitudes/{id}/rechazar` | Rechaza `{administrador, motivo}`: la nota no cambia |
| GET | `/api/auditoria[?notaId=]` | Consulta los registros de auditoría |
| GET | `/api/periodos` | Lista periodos con su estado y los límites de la escala (`escalaMin`, `escalaMax`) |
| GET | `/api/notificaciones` | Últimas 50 notificaciones (más recientes primero): nota, valores, autor, hilo, estado `ENVIADA`/`FALLIDA` (registro en memoria) |
| GET | `/api/notificaciones/simular-fallo` | Estado del fallo simulado `{activo}` (control de demostración) |
| PUT | `/api/notificaciones/simular-fallo` | Activa o desactiva el fallo simulado `{activo: true\|false}` (control de demostración) |

Los errores de negocio y de validación responden `400 {"error": "..."}`; los recursos inexistentes, `404`.

## Estructura del código

```
Notiz/src/main/java/com/notiz/
  NotizApplication.java
  calificaciones/
    presentacion/   NotaController, SolicitudController, PeriodoController, NotaDtos, ManejadorErrores
    negocio/        NotaService, SolicitudService, PeriodoService, ReglasNota, DatosIniciales,
                    NotaResultado, SolicitudResultado, PeriodoResultado, ReglaNegocioException, NoEncontradoException
      eventos/      NotaModificada
    datos/          Nota, Periodo, EstadoPeriodo, Escala, SolicitudCambio, EstadoSolicitud + repositorios
  auditoria/
    presentacion/   AuditoriaController
    negocio/        AuditoriaListener (síncrono), AuditoriaService, RegistroAuditoriaResultado
    datos/          RegistroAuditoria (inmutable), RegistroAuditoriaRepository (solo save y lecturas)
  notificaciones/
    presentacion/   NotificacionController (consulta y control de demostración)
    negocio/        NotificacionListener (asíncrono), NotificacionesRecientes (registro en memoria),
                    SimulacionFallo, NotificacionRegistrada
Notiz/src/main/resources/static/index.html   panel de demostración (página estática, solo usa la API REST)
```

Regla de capas (verificada con ArchUnit): **presentacion -> negocio -> datos**, nunca hacia atrás ni saltándose una capa; los controladores no importan clases de `datos`. `auditoria` y `notificaciones` no dependen de `calificaciones.datos` ni entre sí: solo conocen el evento `NotaModificada`.

## Documentación

Ver [Docs/README.md](Docs/README.md).
