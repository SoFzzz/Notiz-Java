package com.notiz.calificaciones.datos;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("notas")
@Getter
@Setter
@NoArgsConstructor
public class Nota {
    @Id
    private String id;
    private String estudianteId;
    private String materia;
    private double valor;
    private String periodoId;
    private String autor; // profesor que registró o modificó por última vez
    private Instant creada;
    private Instant actualizada;
}
