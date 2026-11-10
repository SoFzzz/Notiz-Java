package com.notiz.auditoria.negocio;

import com.notiz.auditoria.datos.RegistroAuditoria;
import com.notiz.auditoria.datos.RegistroAuditoriaRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class AuditoriaService {

    private final RegistroAuditoriaRepository registros;

    public AuditoriaService(RegistroAuditoriaRepository registros) {
        this.registros = registros;
    }

    public List<RegistroAuditoriaResultado> consultar(String notaId) {
        List<RegistroAuditoria> lista = notaId == null
                ? registros.findAllByOrderByFechaAsc()
                : registros.findByNotaIdOrderByFechaAsc(notaId);
        return lista.stream()
                .map(r -> new RegistroAuditoriaResultado(r.id(), r.notaId(), r.valorAnterior(),
                        r.valorNuevo(), r.autor(), r.motivo(), r.fecha()))
                .toList();
    }
}
