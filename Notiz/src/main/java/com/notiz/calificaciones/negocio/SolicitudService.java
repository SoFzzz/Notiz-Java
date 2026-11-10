package com.notiz.calificaciones.negocio;

import com.notiz.calificaciones.datos.EstadoSolicitud;
import com.notiz.calificaciones.datos.Nota;
import com.notiz.calificaciones.datos.NotaRepository;
import com.notiz.calificaciones.datos.SolicitudCambio;
import com.notiz.calificaciones.datos.SolicitudCambioRepository;
import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.time.Instant;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class SolicitudService {

    private final SolicitudCambioRepository solicitudes;
    private final NotaRepository notas;
    private final ReglasNota reglas;
    private final ApplicationEventPublisher eventos;

    public SolicitudService(SolicitudCambioRepository solicitudes, NotaRepository notas,
                            ReglasNota reglas, ApplicationEventPublisher eventos) {
        this.solicitudes = solicitudes;
        this.notas = notas;
        this.reglas = reglas;
        this.eventos = eventos;
    }

    // Sin login en el esqueleto: profesor y administrador llegan en el cuerpo (autenticación fuera de alcance).
    public SolicitudResultado crear(String notaId, double valorPropuesto, String justificacion, String profesor) {
        Nota nota = buscarNota(notaId);
        reglas.validarEscala(reglas.periodo(nota.getPeriodoId()), valorPropuesto);

        SolicitudCambio s = new SolicitudCambio();
        s.setNotaId(notaId);
        s.setValorPropuesto(valorPropuesto);
        s.setJustificacion(justificacion);
        s.setSolicitante(profesor);
        s.setFechaCreacion(Instant.now());
        return aResultado(solicitudes.save(s));
    }

    public List<SolicitudResultado> listar(String estado) {
        List<SolicitudCambio> lista = estado == null
                ? solicitudes.findAll()
                : solicitudes.findByEstado(parsearEstado(estado));
        return lista.stream().map(SolicitudService::aResultado).toList();
    }

    public SolicitudResultado aprobar(String id, String administrador) {
        SolicitudCambio s = buscarPendiente(id);
        Nota nota = buscarNota(s.getNotaId());
        // Se revalida la escala: pudo cambiar desde que se creó la solicitud.
        reglas.validarEscala(reglas.periodo(nota.getPeriodoId()), s.getValorPropuesto());

        double anterior = nota.getValor();
        Instant ahora = Instant.now();
        nota.setValor(s.getValorPropuesto());
        nota.setAutor(administrador);
        nota.setActualizada(ahora);
        notas.save(nota);

        s.setEstado(EstadoSolicitud.APROBADA);
        s.setRevisor(administrador);
        s.setFechaRevision(ahora);
        s = solicitudes.save(s);

        String motivo = "Solicitud " + s.getId() + " de " + s.getSolicitante() + ": " + s.getJustificacion();
        eventos.publishEvent(new NotaModificada(nota.getId(), anterior, s.getValorPropuesto(),
                administrador, motivo, ahora));
        return aResultado(s);
    }

    public SolicitudResultado rechazar(String id, String administrador, String motivo) {
        SolicitudCambio s = buscarPendiente(id);
        s.setEstado(EstadoSolicitud.RECHAZADA);
        s.setRevisor(administrador);
        s.setFechaRevision(Instant.now());
        s.setMotivoRechazo(motivo);
        return aResultado(solicitudes.save(s));
    }

    private SolicitudCambio buscarPendiente(String id) {
        SolicitudCambio s = solicitudes.findById(id)
                .orElseThrow(() -> new NoEncontradoException("No existe la solicitud " + id));
        if (s.getEstado() != EstadoSolicitud.PENDIENTE) {
            throw new ReglaNegocioException("La solicitud ya fue resuelta");
        }
        return s;
    }

    private Nota buscarNota(String id) {
        return notas.findById(id).orElseThrow(() -> new NoEncontradoException("No existe la nota " + id));
    }

    private static EstadoSolicitud parsearEstado(String estado) {
        try {
            return EstadoSolicitud.valueOf(estado.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new ReglaNegocioException("Estado inválido: " + estado);
        }
    }

    private static SolicitudResultado aResultado(SolicitudCambio s) {
        return new SolicitudResultado(s.getId(), s.getNotaId(), s.getValorPropuesto(), s.getJustificacion(),
                s.getSolicitante(), s.getEstado().name(), s.getFechaCreacion(),
                s.getRevisor(), s.getFechaRevision(), s.getMotivoRechazo());
    }
}
