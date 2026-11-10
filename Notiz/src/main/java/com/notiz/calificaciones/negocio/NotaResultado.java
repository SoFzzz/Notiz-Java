package com.notiz.calificaciones.negocio;

import java.time.Instant;

public record NotaResultado(String id, String estudianteId, String materia, double valor,
                            String periodoId, String autor, Instant creada, Instant actualizada) {
}
