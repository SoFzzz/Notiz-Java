package com.notiz.notificaciones.negocio;

import static org.assertj.core.api.Assertions.assertThat;

import java.time.Instant;
import org.junit.jupiter.api.Test;

class NotificacionesRecientesTest {

    private static NotificacionRegistrada entrada(int n) {
        return new NotificacionRegistrada("N" + n, null, n, "autor", Instant.now(), "hilo", "ENVIADA", "m");
    }

    @Test
    void devuelveLasMasRecientesPrimero() {
        var log = new NotificacionesRecientes();
        log.registrar(entrada(1));
        log.registrar(entrada(2));

        assertThat(log.recientes()).extracting(NotificacionRegistrada::notaId).containsExactly("N2", "N1");
    }

    @Test
    void conservaSoloLasUltimas50() {
        var log = new NotificacionesRecientes();
        for (int i = 1; i <= NotificacionesRecientes.CAPACIDAD + 10; i++) {
            log.registrar(entrada(i));
        }

        assertThat(log.recientes()).hasSize(50);
        assertThat(log.recientes().get(0).notaId()).isEqualTo("N60");
        assertThat(log.recientes().get(49).notaId()).isEqualTo("N11");
    }
}
