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
import com.notiz.calificaciones.datos.EstadoSolicitud;
import com.notiz.calificaciones.datos.Nota;
import com.notiz.calificaciones.datos.NotaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import com.notiz.calificaciones.datos.SolicitudCambio;
import com.notiz.calificaciones.datos.SolicitudCambioRepository;
import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.context.ApplicationEventPublisher;

class SolicitudServiceTest {

    private final SolicitudCambioRepository solicitudes = mock(SolicitudCambioRepository.class);
    private final NotaRepository notas = mock(NotaRepository.class);
    private final PeriodoRepository periodos = mock(PeriodoRepository.class);
    private final EscalaRepository escalas = mock(EscalaRepository.class);
    private final ApplicationEventPublisher eventos = mock(ApplicationEventPublisher.class);
    private SolicitudService servicio;
    private Nota nota;

    @BeforeEach
    void preparar() {
        servicio = new SolicitudService(solicitudes, notas, new ReglasNota(periodos, escalas), eventos);
        when(escalas.findById("E1")).thenReturn(Optional.of(new Escala("E1", "Demo", 1.0, 5.0)));
        when(periodos.findById("P1")).thenReturn(Optional.of(new Periodo("P1", "2026-P1", EstadoPeriodo.CERRADO, "E1")));
        nota = new Nota();
        nota.setId("N1");
        nota.setPeriodoId("P1");
        nota.setValor(2.8);
        when(notas.findById("N1")).thenReturn(Optional.of(nota));
        when(solicitudes.save(any(SolicitudCambio.class))).thenAnswer(i -> i.getArgument(0));
    }

    private SolicitudCambio pendiente() {
        SolicitudCambio s = new SolicitudCambio();
        s.setId("S1");
        s.setNotaId("N1");
        s.setValorPropuesto(4.0);
        s.setJustificacion("Error de digitación");
        s.setSolicitante("prof");
        when(solicitudes.findById("S1")).thenReturn(Optional.of(s));
        return s;
    }

    @Test
    void crearSolicitudEnPeriodoCerradoQuedaPendiente() {
        SolicitudResultado r = servicio.crear("N1", 4.0, "Error de digitación", "prof");

        assertThat(r.estado()).isEqualTo("PENDIENTE");
        verify(eventos, never()).publishEvent(any(Object.class));
    }

    @Test
    void crearSolicitudFueraDeEscalaLanzaExcepcion() {
        assertThatThrownBy(() -> servicio.crear("N1", 9.0, "Error de digitación", "prof"))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessageContaining("fuera de la escala");
        verify(solicitudes, never()).save(any());
    }

    @Test
    void aprobarAplicaCambioYPublicaEvento() {
        pendiente();

        SolicitudResultado r = servicio.aprobar("S1", "admin");

        assertThat(r.estado()).isEqualTo("APROBADA");
        assertThat(nota.getValor()).isEqualTo(4.0);
        verify(notas).save(nota);
        ArgumentCaptor<NotaModificada> captor = ArgumentCaptor.forClass(NotaModificada.class);
        verify(eventos).publishEvent(captor.capture());
        assertThat(captor.getValue().valorAnterior()).isEqualTo(2.8);
        assertThat(captor.getValue().valorNuevo()).isEqualTo(4.0);
        assertThat(captor.getValue().autor()).isEqualTo("admin");
        assertThat(captor.getValue().motivo()).contains("S1").contains("prof");
    }

    @Test
    void rechazarNoCambiaLaNotaNiPublicaEvento() {
        pendiente();

        SolicitudResultado r = servicio.rechazar("S1", "admin", "Sin sustento");

        assertThat(r.estado()).isEqualTo("RECHAZADA");
        assertThat(nota.getValor()).isEqualTo(2.8);
        verify(notas, never()).save(any());
        verify(eventos, never()).publishEvent(any(Object.class));
    }

    @Test
    void aprobarDosVecesLanzaExcepcion() {
        SolicitudCambio s = pendiente();

        servicio.aprobar("S1", "admin");
        assertThat(s.getEstado()).isEqualTo(EstadoSolicitud.APROBADA);

        assertThatThrownBy(() -> servicio.aprobar("S1", "admin"))
                .isInstanceOf(ReglaNegocioException.class)
                .hasMessage("La solicitud ya fue resuelta");
    }
}
