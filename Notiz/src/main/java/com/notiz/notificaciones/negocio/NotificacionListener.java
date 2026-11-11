package com.notiz.notificaciones.negocio;

import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacionListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionListener.class);

    private final SimulacionFallo simulacion;
    private final NotificacionesRecientes recientes;

    public NotificacionListener(SimulacionFallo simulacion, NotificacionesRecientes recientes) {
        this.simulacion = simulacion;
        this.recientes = recientes;
    }

    /** Asíncrono: un fallo aquí nunca debe afectar a la nota ya guardada ni a la respuesta HTTP. */
    @Async
    @EventListener
    public void alModificarseNota(NotaModificada e) {
        log.info("[NOTIFICACION] Nota {} cambió de {} a {} (autor {})",
                e.notaId(), e.valorAnterior(), e.valorNuevo(), e.autor());
        String hilo = Thread.currentThread().getName();
        if (simulacion.activo()) {
            log.warn("[NOTIFICACION] fallo simulado");
            recientes.registrar(new NotificacionRegistrada(e.notaId(), e.valorAnterior(), e.valorNuevo(),
                    e.autor(), Instant.now(), hilo, NotificacionRegistrada.FALLIDA, "Fallo simulado al notificar"));
            throw new RuntimeException("Fallo simulado al notificar");
        }
        recientes.registrar(new NotificacionRegistrada(e.notaId(), e.valorAnterior(), e.valorNuevo(),
                e.autor(), Instant.now(), hilo, NotificacionRegistrada.ENVIADA,
                mensajeEnviada(e)));
    }

    private static String mensajeEnviada(NotaModificada e) {
        if (e.valorAnterior() == null) {
            return "Nota " + e.notaId() + " creada con valor " + e.valorNuevo();
        }
        return "Nota " + e.notaId() + " cambió de " + e.valorAnterior() + " a " + e.valorNuevo();
    }
}
