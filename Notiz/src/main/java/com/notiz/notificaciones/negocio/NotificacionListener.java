package com.notiz.notificaciones.negocio;

import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

@Component
public class NotificacionListener {

    private static final Logger log = LoggerFactory.getLogger(NotificacionListener.class);

    private final boolean simularFallo;

    public NotificacionListener(@Value("${notiz.notificaciones.simular-fallo:false}") boolean simularFallo) {
        this.simularFallo = simularFallo;
    }

    /** Asíncrono: un fallo aquí nunca debe afectar a la nota ya guardada ni a la respuesta HTTP. */
    @Async
    @EventListener
    public void alModificarseNota(NotaModificada e) {
        log.info("[NOTIFICACION] Nota {} cambió de {} a {} (autor {})",
                e.notaId(), e.valorAnterior(), e.valorNuevo(), e.autor());
        if (simularFallo) {
            log.warn("[NOTIFICACION] fallo simulado");
            throw new RuntimeException("Fallo simulado al notificar");
        }
    }
}
