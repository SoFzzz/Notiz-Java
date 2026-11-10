package com.notiz.calificaciones.presentacion;

import com.notiz.calificaciones.negocio.NotaResultado;
import com.notiz.calificaciones.negocio.NotaService;
import com.notiz.calificaciones.negocio.SolicitudResultado;
import com.notiz.calificaciones.negocio.SolicitudService;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/notas")
public class NotaController {

    private final NotaService notas;
    private final SolicitudService solicitudes;

    public NotaController(NotaService notas, SolicitudService solicitudes) {
        this.notas = notas;
        this.solicitudes = solicitudes;
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public NotaResultado crear(@Valid @RequestBody NotaDtos.Crear dto) {
        return notas.crear(dto.estudianteId(), dto.materia(), dto.periodoId(), dto.valor(), dto.profesor());
    }

    @PutMapping("/{id}")
    public NotaResultado editar(@PathVariable String id, @Valid @RequestBody NotaDtos.Editar dto) {
        return notas.editar(id, dto.valor(), dto.profesor());
    }

    @GetMapping
    public List<NotaResultado> listar(@RequestParam(required = false) String periodoId) {
        return notas.listar(periodoId);
    }

    @GetMapping("/{id}")
    public NotaResultado obtener(@PathVariable String id) {
        return notas.obtener(id);
    }

    @PostMapping("/{id}/solicitudes")
    @ResponseStatus(HttpStatus.CREATED)
    public SolicitudResultado solicitarCambio(@PathVariable String id,
                                              @Valid @RequestBody NotaDtos.Solicitud dto) {
        return solicitudes.crear(id, dto.valorPropuesto(), dto.justificacion(), dto.profesor());
    }
}
