package com.notiz.calificaciones.datos;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SolicitudCambioRepository extends MongoRepository<SolicitudCambio, String> {
    List<SolicitudCambio> findByEstado(EstadoSolicitud estado);
}
