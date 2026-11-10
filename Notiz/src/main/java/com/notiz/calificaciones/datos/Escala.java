package com.notiz.calificaciones.datos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("escalas")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Escala {
    @Id
    private String id;
    private String nombre;
    private double min;
    private double max;

    public boolean contiene(double valor) {
        return valor >= min && valor <= max;
    }
}
