package com.notiz.calificaciones.negocio;

import com.notiz.calificaciones.datos.EscalaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class PeriodoService {

    private final PeriodoRepository periodos;
    private final EscalaRepository escalas;

    public PeriodoService(PeriodoRepository periodos, EscalaRepository escalas) {
        this.periodos = periodos;
        this.escalas = escalas;
    }

    public List<PeriodoResultado> listar() {
        return periodos.findAll().stream().map(this::aResultado).toList();
    }

    private PeriodoResultado aResultado(Periodo p) {
        var escala = escalas.findById(p.getEscalaId())
                .orElseThrow(() -> new NoEncontradoException("No existe la escala " + p.getEscalaId()));
        return new PeriodoResultado(p.getId(), p.getNombre(), p.getEstado().name(), escala.getMin(), escala.getMax());
    }
}
