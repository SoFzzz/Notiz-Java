workspace "Notiz" "Gestión de notas multi-institución (esqueleto funcional)" {

    model {
        profesor = person "Profesor" "Registra y corrige notas"
        admin = person "Administrador (coordinación)" "Aprueba o rechaza cambios de nota en periodo cerrado y consulta la auditoría"
        estudiante = person "Estudiante" "Consulta sus notas (fuera del esqueleto)" "FueraDelEsqueleto"
        acudiente = person "Acudiente" "Consulta las notas de su hijo (fuera del esqueleto)" "FueraDelEsqueleto"

        correo = softwareSystem "Servidor de correo" "Envía avisos (fuera del esqueleto)" "FueraDelEsqueleto"

        notiz = softwareSystem "Notiz" "Gestión de notas multi-institución con trazabilidad" {
            app = container "Aplicación Notiz" "Monolito modular en capas; un solo proceso. Módulos: calificaciones, auditoría, notificaciones" "Spring Boot 4.0.6 / Java 21" {
                notaController = component "NotaController" "REST /api/notas" "Spring MVC"
                solicitudController = component "SolicitudController" "REST /api/solicitudes" "Spring MVC"
                notaService = component "NotaService" "Crear y editar notas; publica NotaModificada" "Spring Service"
                solicitudService = component "SolicitudService" "Solicitudes de cambio; al aprobar guarda nota y solicitud y publica NotaModificada (no llama a NotaService)" "Spring Service"
                reglasNota = component "ReglasNota" "Reglas compartidas: periodo, exigirAbierto, validarEscala" "Spring Component"
                repositoriosCalificaciones = component "Repositorios (Spring Data)" "Nota, Periodo, Escala y SolicitudCambio" "Spring Data MongoDB"
                auditoriaListener = component "AuditoriaListener" "Guarda RegistroAuditoria (síncrono, append-only)" "@EventListener"
                auditoriaService = component "AuditoriaService" "Consulta de registros de auditoría" "Spring Service"
                auditoriaController = component "AuditoriaController" "GET /api/auditoria" "Spring MVC"
                repositorioAuditoria = component "Repositorios (Spring Data) de auditoría" "RegistroAuditoriaRepository" "Spring Data MongoDB"
                notificacionListener = component "NotificacionListener" "Notifica (solo log en el esqueleto) y anota ENVIADA/FALLIDA con el hilo; requiere @EnableAsync en NotizApplication" "@Async @EventListener"
                notificacionesRecientes = component "NotificacionesRecientes" "Registro en memoria de las últimas 50 notificaciones" "Spring Component"
                notificacionController = component "NotificacionController" "GET /api/notificaciones; GET y PUT /api/notificaciones/simular-fallo (control de demostración)" "Spring MVC"
                panel = component "Panel de demostración" "Página estática index.html servida por la misma aplicación (no es un contenedor); solo usa la API REST" "HTML/JS"
            }
            db = container "MongoDB 7" "Notas, periodos, escalas, solicitudes y auditoría" "MongoDB 7" "Database"
        }

        profesor -> app "Registra notas y crea solicitudes" "HTTP/JSON"
        admin -> app "Aprueba/rechaza solicitudes y consulta auditoría" "HTTP/JSON"
        estudiante -> notiz "Consulta notas" "HTTP"
        acudiente -> notiz "Consulta notas del hijo" "HTTP"
        app -> db "Lee y escribe documentos" "Protocolo Mongo, 27017"
        app -> correo "Enviaría avisos" "SMTP"

        profesor -> notaController "Registra notas" "HTTP/JSON"
        profesor -> solicitudController "Crea solicitudes de cambio" "HTTP/JSON"
        admin -> solicitudController "Aprueba/rechaza" "HTTP/JSON"
        admin -> auditoriaController "Consulta auditoría" "HTTP/JSON"
        notaController -> notaService "Usa"
        solicitudController -> solicitudService "Usa"
        notaService -> reglasNota "Valida periodo y escala"
        solicitudService -> reglasNota "Valida periodo y escala"
        notaService -> repositoriosCalificaciones "Guarda notas"
        solicitudService -> repositoriosCalificaciones "Guarda notas y solicitudes"
        reglasNota -> repositoriosCalificaciones "Lee periodos y escalas"
        notaService -> auditoriaListener "publica NotaModificada (evento en memoria, ApplicationEventPublisher)" "mismo hilo"
        notaService -> notificacionListener "publica NotaModificada (evento en memoria, ApplicationEventPublisher)" "otro hilo, @Async"
        solicitudService -> auditoriaListener "publica NotaModificada (evento en memoria, ApplicationEventPublisher)" "mismo hilo"
        solicitudService -> notificacionListener "publica NotaModificada (evento en memoria, ApplicationEventPublisher)" "otro hilo, @Async"
        auditoriaListener -> repositorioAuditoria "Inserta registros"
        notificacionListener -> notificacionesRecientes "Anota cada notificación"
        notificacionController -> notificacionesRecientes "Lee el registro y cambia el fallo simulado"
        profesor -> panel "Ejecuta la demostración" "HTTP"
        panel -> notaController "Llama" "HTTP/JSON"
        panel -> solicitudController "Llama" "HTTP/JSON"
        panel -> auditoriaController "Llama" "HTTP/JSON"
        panel -> notificacionController "Llama" "HTTP/JSON"
        auditoriaController -> auditoriaService "Usa"
        auditoriaService -> repositorioAuditoria "Lee registros"
        repositoriosCalificaciones -> db "Lee y escribe documentos"
        repositorioAuditoria -> db "Lee y escribe documentos"
    }

    views {
        systemContext notiz "Nivel1" "C4 nivel 1 - Contexto" {
            include *
            autolayout lr
        }
        container notiz "Nivel2" "C4 nivel 2 - Contenedores (= docker-compose: app, mongo). Estudiante y Acudiente se omiten: sin interacción en el esqueleto" {
            include profesor admin app db correo
            autolayout tb
        }
        component app "Nivel3" "C4 nivel 3 (opcional) - Componentes. El evento NotaModificada es el bus en proceso, no un contenedor" {
            include *
            autolayout lr
        }
        styles {
            element "Person" {
                shape person
            }
            element "Database" {
                shape cylinder
            }
            element "FueraDelEsqueleto" {
                background #999999
                color #ffffff
                border dashed
            }
        }
    }
}
