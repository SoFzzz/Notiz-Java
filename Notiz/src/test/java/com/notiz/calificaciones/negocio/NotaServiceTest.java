package com.notiz.calificaciones.negocio;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.notiz.calificaciones.datos.Escala;
import com.notiz.calificaciones.datos.EscalaRepository;
import com.notiz.calificaciones.datos.EstadoPeriodo;
import com.notiz.calificaciones.datos.Nota;
import com.notiz.calificaciones.datos.NotaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

class NotaServiceTest {

    private final NotaRepository notas = mock(NotaRepository.class);
    private final PeriodoRepository periodos = mock(PeriodoRepository.class);
    private final EscalaRepository escalas = mock(EscalaRepository.class);
    private final ApplicationEventPublisher eventos = mock(ApplicationEventPublisher.class);
    private NotaService servicio;

    @BeforeEach
    void preparar() {
        servicio = new NotaService(notas, new ReglasNota(periodos, escalas), eventos);
        when(escalas.findById("E1")).thenReturn(Optional.of(new Escala("E1", "Demo", 1.0, 5.0)));
        when(periodos.findById("P1")).thenReturn(Optional.of(new Periodo("P1", "2026-P1", EstadoPeriodo.CERRADO, "E1")));
        when(periodos.findById("P2")).thenReturn(Optional.of(new Periodo("P2", "2026-P2", EstadoPeriodo.ABIERTO, "E1")));
        when(notas.save(any(Nota.class))).thenAnswer(i -> i.getArgument(0));
    }

    @Test
    void crearNotaValidaPublicaEvento() {
        NotaResultado r = servicio.crear("EST-1", "Matemáticas", "P2", 4.0, "prof");

        assertThat(r.valor()).isEqualTo(4.0);
        ArgumentCaptor<NotaModificada> captor = ArgumentCaptor.forClass(NotaModificada.class);
        verify(eventos).publishEvent(captor.capture());
        assertThat(captor.getValue().valorAnterior()).isNull();
        assertThat(captor.getValue().valorNuevo()).isEqualTo(4.0);
        assertThat(captor.getValue().autor()).isEqualTo("prof");
    }

    @Test
    void crearFueraDeEscalaLanzaExcepcion() {
        assertThatThrownBy(() -> servicio.crear("EST-1", "Matemáticas", "P2", 7.0, "prof"))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessage("El valor 7.0 está fuera de la escala 1.0-5.0");
        verify(notas, never()).save(any());
        verify(eventos, never()).publishEvent(any(Object.class));
    }

    @Test
    void crearEnPeriodoCerradoLanzaExcepcion() {
        assertThatThrownBy(() -> servicio.crear("EST-1", "Matemáticas", "P1", 4.0, "prof"))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessage("El periodo 2026-P1 está cerrado: haga una solicitud de cambio");
        verify(eventos, never()).publishEvent(any(Object.class));
    }

    @Test
    void crearEnPeriodoInexistenteLanzaNoEncontrado() {
        assertThatThrownBy(() -> servicio.crear("EST-1", "Matemáticas", "PX", 4.0, "prof"))
                .isInstanceOf(NoEncontradoException.class);
    }

    @Test
    void editarEnPeriodoAbiertoPublicaValorAnterior() {
        Nota nota = nota("N2", "P2", 3.0);
        when(notas.findById("N2")).thenReturn(Optional.of(nota));

        servicio.editar("N2", 4.5, "prof");

        ArgumentCaptor<NotaModificada> captor = ArgumentCaptor.forClass(NotaModificada.class);
        verify(eventos).publishEvent(captor.capture());
        assertThat(captor.getValue().valorAnterior()).isEqualTo(3.0);
        assertThat(captor.getValue().valorNuevo()).isEqualTo(4.5);
    }

    @Test
    void editarEnPeriodoCerradoLanzaExcepcion() {
        when(notas.findById("N1")).thenReturn(Optional.of(nota("N1", "P1", 2.8)));

        assertThatThrownBy(() -> servicio.editar("N1", 4.0, "prof"))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("haga una solicitud");
        verify(eventos, never()).publishEvent(any(Object.class));
    }

    static Nota nota(String id, String periodoId, double valor) {
        Nota n = new Nota();
        n.setId(id);
        n.setPeriodoId(periodoId);
        n.setValor(valor);
        return n;
    }
}
