package com.notiz.auditoria.negocio;

import java.time.Instant;

public record RegistroAuditoriaResultado(String id, String notaId, Double valorAnterior, double valorNuevo,
                                         String autor, String motivo, Instant fecha) {
}
