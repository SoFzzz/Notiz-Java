package com.notiz.calificaciones.datos;

import java.time.Instant;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("solicitudes")
@Getter
@Setter
@NoArgsConstructor
public class SolicitudCambio {
    @Id
    private String id;
    private String notaId;
    private double valorPropuesto;
    private String justificacion;
    private String solicitante;
    private EstadoSolicitud estado = EstadoSolicitud.PENDIENTE;
    private Instant fechaCreacion;
    private String revisor;
    private Instant fechaRevision;
    private String motivoRechazo;
}
