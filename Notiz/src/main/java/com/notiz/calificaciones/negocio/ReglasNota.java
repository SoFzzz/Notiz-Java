package com.notiz.calificaciones.negocio;

import com.notiz.calificaciones.datos.Escala;
import com.notiz.calificaciones.datos.EscalaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import org.springframework.stereotype.Component;

/** Reglas compartidas: existencia del periodo, periodo abierto y valor dentro de la escala. */
@Component
public class ReglasNota {

    private final PeriodoRepository periodos;
    private final EscalaRepository escalas;

    public ReglasNota(PeriodoRepository periodos, EscalaRepository escalas) {
        this.periodos = periodos;
        this.escalas = escalas;
    }

    public Periodo periodo(String periodoId) {
        return periodos.findById(periodoId)
                .orElseThrow(() -> new NoEncontradoException("No existe el periodo " + periodoId));
    }

    public void exigirAbierto(Periodo periodo) {
        if (periodo.estaCerrado()) {
            throw new ReglaNegocioException(
                    "El periodo " + periodo.getNombre() + " está cerrado: haga una solicitud de cambio");
        }
    }

    public void validarEscala(Periodo periodo, double valor) {
        Escala escala = escalas.findById(periodo.getEscalaId())
                .orElseThrow(() -> new NoEncontradoException("No existe la escala " + periodo.getEscalaId()));
        if (!escala.contiene(valor)) {
            throw new ReglaNegocioException("El valor " + valor + " está fuera de la escala "
                    + escala.getMin() + "-" + escala.getMax());
        }
    }
}
