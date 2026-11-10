# Notiz

Notiz es una plataforma web de gestión académica institucional: los profesores registran calificaciones, los administradores controlan los cambios sobre periodos cerrados y cada modificación queda auditada. Proyecto integrador de Ingeniería de Software, Universidad Cooperativa de Colombia (UCC).

- Autora: Paula Sofía González Zambrano
- Integrantes del trío: (completar)

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

### Simular una falla de notificación

```bash
NOTIZ_NOTIFICACIONES_SIMULARFALLO=true docker compose up --build
```

(En PowerShell: `$env:NOTIZ_NOTIFICACIONES_SIMULARFALLO="true"; docker compose up --build`.) La notificación registra `[NOTIFICACION] fallo simulado` y lanza una excepción, pero la nota se guarda y la auditoría se registra igual.

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

Los errores de negocio y de validación responden `400 {"error": "..."}`; los recursos inexistentes, `404`.

## Estructura del código

```
Notiz/src/main/java/com/notiz/
  NotizApplication.java
  calificaciones/
    presentacion/   NotaController, SolicitudController, NotaDtos, ManejadorErrores
    negocio/        NotaService, SolicitudService, ReglasNota, DatosIniciales,
                    NotaResultado, SolicitudResultado, ReglaNegocioException, NoEncontradoException
      eventos/      NotaModificada
    datos/          Nota, Periodo, EstadoPeriodo, Escala, SolicitudCambio, EstadoSolicitud + repositorios
  auditoria/
    presentacion/   AuditoriaController
    negocio/        AuditoriaListener (síncrono), AuditoriaService, RegistroAuditoriaResultado
    datos/          RegistroAuditoria (inmutable), RegistroAuditoriaRepository (solo save y lecturas)
  notificaciones/
    negocio/        NotificacionListener (asíncrono)
```

Regla de capas (verificada con ArchUnit): **presentacion -> negocio -> datos**, nunca hacia atrás ni saltándose una capa; los controladores no importan clases de `datos`. `auditoria` y `notificaciones` no dependen de `calificaciones.datos` ni entre sí: solo conocen el evento `NotaModificada`.

## Documentación

Ver [Docs/README.md](Docs/README.md).
