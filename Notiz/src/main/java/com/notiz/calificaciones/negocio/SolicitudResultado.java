package com.notiz.calificaciones.negocio;

import java.time.Instant;

public record SolicitudResultado(String id, String notaId, double valorPropuesto, String justificacion,
                                 String solicitante, String estado, Instant fechaCreacion,
                                 String revisor, Instant fechaRevision, String motivoRechazo) {
}
