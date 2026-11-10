package com.notiz;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import com.notiz.notificaciones.negocio.NotificacionListener;
import org.junit.jupiter.api.Test;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;

/** Garantiza que el listener es asíncrono Y que @Async está activado en la aplicación. */
class AsincroniaNotificacionTest {

    @Test
    void asyncEstaHabilitadoYElListenerEsAsincrono() throws Exception {
        assertNotNull(NotizApplication.class.getAnnotation(EnableAsync.class));
        assertNotNull(NotificacionListener.class
                .getMethod("alModificarseNota", NotaModificada.class)
                .getAnnotation(Async.class));
    }
}
