package com.notiz.calificaciones.negocio;

import com.notiz.calificaciones.datos.Nota;
import com.notiz.calificaciones.datos.NotaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.negocio.eventos.NotaModificada;
import java.time.Instant;
import java.util.List;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Service;

@Service
public class NotaService {

    private final NotaRepository notas;
    private final ReglasNota reglas;
    private final ApplicationEventPublisher eventos;

    public NotaService(NotaRepository notas, ReglasNota reglas, ApplicationEventPublisher eventos) {
        this.notas = notas;
        this.reglas = reglas;
        this.eventos = eventos;
    }

    // Sin login en el esqueleto: el profesor llega en el cuerpo (la autenticación está fuera de alcance).
    public NotaResultado crear(String estudianteId, String materia, String periodoId, double valor, String profesor) {
        Periodo periodo = reglas.periodo(periodoId);
        reglas.exigirAbierto(periodo);
        reglas.validarEscala(periodo, valor);

        Instant ahora = Instant.now();
        Nota nota = new Nota();
        nota.setEstudianteId(estudianteId);
        nota.setMateria(materia);
        nota.setPeriodoId(periodoId);
        nota.setValor(valor);
        nota.setAutor(profesor);
        nota.setCreada(ahora);
        nota.setActualizada(ahora);
        nota = notas.save(nota);

        eventos.publishEvent(new NotaModificada(nota.getId(), null, valor, profesor, "Creación de nota", ahora));
        return aResultado(nota);
    }

    public NotaResultado editar(String id, double valor, String profesor) {
        Nota nota = buscar(id);
        Periodo periodo = reglas.periodo(nota.getPeriodoId());
        reglas.exigirAbierto(periodo);
        reglas.validarEscala(periodo, valor);

        double anterior = nota.getValor();
        Instant ahora = Instant.now();
        nota.setValor(valor);
        nota.setAutor(profesor);
        nota.setActualizada(ahora);
        nota = notas.save(nota);

        eventos.publishEvent(new NotaModificada(id, anterior, valor, profesor, "Edición directa", ahora));
        return aResultado(nota);
    }

    public List<NotaResultado> listar(String periodoId) {
        List<Nota> lista = periodoId == null ? notas.findAll() : notas.findByPeriodoId(periodoId);
        return lista.stream().map(NotaService::aResultado).toList();
    }

    public NotaResultado obtener(String id) {
        return aResultado(buscar(id));
    }

    private Nota buscar(String id) {
        return notas.findById(id).orElseThrow(() -> new NoEncontradoException("No existe la nota " + id));
    }

    private static NotaResultado aResultado(Nota n) {
        return new NotaResultado(n.getId(), n.getEstudianteId(), n.getMateria(), n.getValor(),
                n.getPeriodoId(), n.getAutor(), n.getCreada(), n.getActualizada());
    }
}
