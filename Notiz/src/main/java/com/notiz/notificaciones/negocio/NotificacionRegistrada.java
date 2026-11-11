package com.notiz.notificaciones.negocio;

import java.time.Instant;

/** Una entrada del registro reciente de notificaciones (estado: ENVIADA o FALLIDA). */
public record NotificacionRegistrada(String notaId, Double valorAnterior, double valorNuevo, String autor,
                                     Instant fecha, String hilo, String estado, String mensaje) {

    public static final String ENVIADA = "ENVIADA";
    public static final String FALLIDA = "FALLIDA";
}
