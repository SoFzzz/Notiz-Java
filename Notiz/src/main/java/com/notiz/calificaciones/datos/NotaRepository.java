package com.notiz.calificaciones.datos;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface NotaRepository extends MongoRepository<Nota, String> {
    List<Nota> findByPeriodoId(String periodoId);
}
