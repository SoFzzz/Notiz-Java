package com.notiz.notificaciones.negocio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.time.Instant;
import org.junit.jupiter.api.Test;

class NotificacionListenerTest {

    private final NotificacionesRecientes recientes = new NotificacionesRecientes();
    private final NotaModificada evento = new NotaModificada("N1", 2.8, 4.0, "admin.lopez", "motivo", Instant.now());

    @Test
    void registraEnviadaCuandoNoHayFallo() {
        new NotificacionListener(new SimulacionFallo(false), recientes).alModificarseNota(evento);

        assertThat(recientes.recientes()).hasSize(1);
        NotificacionRegistrada r = recientes.recientes().get(0);
        assertThat(r.estado()).isEqualTo("ENVIADA");
        assertThat(r.notaId()).isEqualTo("N1");
        assertThat(r.valorAnterior()).isEqualTo(2.8);
        assertThat(r.hilo()).isEqualTo(Thread.currentThread().getName());
    }

    @Test
    void registraFallidaYLanzaCuandoElFalloEstaActivo() {
        var listener = new NotificacionListener(new SimulacionFallo(true), recientes);

        assertThatThrownBy(() -> listener.alModificarseNota(evento)).isInstanceOf(RuntimeException.class);

        assertThat(recientes.recientes()).singleElement()
                .satisfies(r -> assertThat(r.estado()).isEqualTo("FALLIDA"));
    }

    @Test
    void elInterruptorSeCambiaEnCaliente() {
        var simulacion = new SimulacionFallo(false);
        var listener = new NotificacionListener(simulacion, recientes);
        simulacion.activar(true);

        assertThatThrownBy(() -> listener.alModificarseNota(evento)).isInstanceOf(RuntimeException.class);
        simulacion.activar(false);
        listener.alModificarseNota(evento);

        assertThat(recientes.recientes()).extracting(NotificacionRegistrada::estado)
                .containsExactly("ENVIADA", "FALLIDA");
    }
}
