package com.notiz.auditoria.negocio;

import com.notiz.auditoria.datos.RegistroAuditoria;
import com.notiz.auditoria.datos.RegistroAuditoriaRepository;
import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class AuditoriaListener {

    private final RegistroAuditoriaRepository registros;

    public AuditoriaListener(RegistroAuditoriaRepository registros) {
        this.registros = registros;
    }

    /**
     * SÍNCRONO a propósito: corre en el mismo hilo de la petición. Si la auditoría falla, la
     * petición HTTP falla. Riesgo conocido: sin transacciones de Mongo (requieren replica set)
     * la nota ya pudo haberse guardado; ver Docs/06-riesgos.md (R1).
     */
    @EventListener
    public void alModificarseNota(NotaModificada e) {
        registros.save(new RegistroAuditoria(null, e.notaId(), e.valorAnterior(), e.valorNuevo(),
                e.autor(), e.motivo(), e.fecha()));
    }
}
