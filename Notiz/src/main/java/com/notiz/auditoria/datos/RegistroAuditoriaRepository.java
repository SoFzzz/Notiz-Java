package com.notiz.auditoria.datos;

import java.util.List;
import org.springframework.data.repository.Repository;

/** Solo altas y lecturas: a propósito no hereda de CrudRepository (sin delete). */
public interface RegistroAuditoriaRepository extends Repository<RegistroAuditoria, String> {

    RegistroAuditoria save(RegistroAuditoria registro);

    List<RegistroAuditoria> findAllByOrderByFechaAsc();

    List<RegistroAuditoria> findByNotaIdOrderByFechaAsc(String notaId);
}
