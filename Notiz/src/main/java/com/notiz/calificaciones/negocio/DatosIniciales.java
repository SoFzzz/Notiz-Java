package com.notiz.calificaciones.negocio;

import com.notiz.calificaciones.datos.Escala;
import com.notiz.calificaciones.datos.EscalaRepository;
import com.notiz.calificaciones.datos.EstadoPeriodo;
import com.notiz.calificaciones.datos.Nota;
import com.notiz.calificaciones.datos.NotaRepository;
import com.notiz.calificaciones.datos.Periodo;
import com.notiz.calificaciones.datos.PeriodoRepository;
import java.time.Instant;
import java.util.List;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/** Datos de demostración. Idempotente: solo inserta si la colección está vacía. */
@Component
public class DatosIniciales implements CommandLineRunner {

    private final EscalaRepository escalas;
    private final PeriodoRepository periodos;
    private final NotaRepository notas;

    public DatosIniciales(EscalaRepository escalas, PeriodoRepository periodos, NotaRepository notas) {
        this.escalas = escalas;
        this.periodos = periodos;
        this.notas = notas;
    }

    @Override
    public void run(String... args) {
        if (escalas.count() == 0) {
            escalas.save(new Escala("E1", "Colegio Demo 1.0-5.0", 1.0, 5.0));
        }
        if (periodos.count() == 0) {
            periodos.saveAll(List.of(
                    new Periodo("P1", "2026-P1", EstadoPeriodo.CERRADO, "E1"),
                    new Periodo("P2", "2026-P2", EstadoPeriodo.ABIERTO, "E1")));
        }
        if (notas.count() == 0) {
            Nota n = new Nota();
            n.setId("N1");
            n.setEstudianteId("EST-001");
            n.setMateria("Matemáticas");
            n.setValor(2.8);
            n.setPeriodoId("P1");
            n.setAutor("prof.ramirez");
            n.setCreada(Instant.now());
            n.setActualizada(n.getCreada());
            notas.save(n);
        }
    }
}
