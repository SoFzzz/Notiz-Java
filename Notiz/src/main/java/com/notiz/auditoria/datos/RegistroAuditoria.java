package com.notiz.auditoria.datos;

import java.time.Instant;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/** Registro inmutable: sin setters; la aplicación solo lo crea y lo lee. */
@Document("auditoria")
public record RegistroAuditoria(@Id String id, String notaId, Double valorAnterior, double valorNuevo,
                                String autor, String motivo, Instant fecha) {
}
