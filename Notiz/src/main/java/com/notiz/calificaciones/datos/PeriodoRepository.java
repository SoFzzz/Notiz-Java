package com.notiz.calificaciones.datos;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface PeriodoRepository extends MongoRepository<Periodo, String> {
}
