package com.notiz.calificaciones.datos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("periodos")
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class Periodo {
    @Id
    private String id;
    private String nombre;
    private EstadoPeriodo estado;
    private String escalaId;

    public boolean estaCerrado() {
        return estado == EstadoPeriodo.CERRADO;
    }
}
