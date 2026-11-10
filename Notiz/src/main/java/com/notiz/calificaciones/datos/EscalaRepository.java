package com.notiz.calificaciones.datos;

import org.springframework.data.mongodb.repository.MongoRepository;

public interface EscalaRepository extends MongoRepository<Escala, String> {
}
