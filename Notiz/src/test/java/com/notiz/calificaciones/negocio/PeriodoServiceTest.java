package com.notiz.calificaciones.negocio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import com.notiz.calificaciones.datos.Escala;
import com.notiz.calificaciones.datos.EscalaRepository;
import com.notiz.calificaciones.datos.EstadoPeriodo;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;

class PeriodoServiceTest {

    @Test
    void listaPeriodosConLaEscala() {
        var periodos = mock(PeriodoRepository.class);
        var escalas = mock(EscalaRepository.class);
        when(periodos.findAll()).thenReturn(List.of(
                new Periodo("P1", "2026-P1", EstadoPeriodo.CERRADO, "E1"),
                new Periodo("P2", "2026-P2", EstadoPeriodo.ABIERTO, "E1")));
        when(escalas.findById("E1")).thenReturn(Optional.of(new Escala("E1", "Demo", 1.0, 5.0)));

        List<PeriodoResultado> r = new PeriodoService(periodos, escalas).listar();

        assertThat(r).containsExactly(
                new PeriodoResultado("P1", "2026-P1", "CERRADO", 1.0, 5.0),
                new PeriodoResultado("P2", "2026-P2", "ABIERTO", 1.0, 5.0));
    }
}
