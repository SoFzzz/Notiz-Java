package com.notiz.calificaciones.presentacion;

import com.notiz.calificaciones.negocio.PeriodoResultado;
import com.notiz.calificaciones.negocio.PeriodoService;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/periodos")
public class PeriodoController {

    private final PeriodoService periodos;

    public PeriodoController(PeriodoService periodos) {
        this.periodos = periodos;
    }

    @GetMapping
    public List<PeriodoResultado> listar() {
        return periodos.listar();
    }
}
