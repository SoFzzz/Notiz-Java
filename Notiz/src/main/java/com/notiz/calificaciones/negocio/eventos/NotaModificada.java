package com.notiz.calificaciones.negocio.eventos;

import java.time.Instant;

/**
 * Contrato publicado por calificaciones: auditoría y notificaciones solo dependen de este evento.
 * valorAnterior es null cuando la nota se acaba de crear.
 */
public record NotaModificada(String notaId, Double valorAnterior, double valorNuevo,
                             String autor, String motivo, Instant fecha) {
}
