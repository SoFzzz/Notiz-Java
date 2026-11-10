package com.notiz.calificaciones.presentacion;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

/** Cuerpos de petición de /api/notas. */
public final class NotaDtos {

    private NotaDtos() {
    }

    public record Crear(@NotBlank String estudianteId, @NotBlank String materia,
                        @NotBlank String periodoId, @NotNull Double valor, @NotBlank String profesor) {
    }

    public record Editar(@NotNull Double valor, @NotBlank String profesor) {
    }

    public record Solicitud(@NotNull Double valorPropuesto,
                            @NotBlank @Size(min = 10, message = "debe tener al menos 10 caracteres") String justificacion,
                            @NotBlank String profesor) {
    }
}
