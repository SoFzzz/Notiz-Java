package com.notiz.calificaciones.presentacion;

import com.notiz.calificaciones.negocio.SolicitudResultado;
import com.notiz.calificaciones.negocio.SolicitudService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/solicitudes")
public class SolicitudController {

    public record Aprobar(@NotBlank String administrador) {
    }

    public record Rechazar(@NotBlank String administrador, @NotBlank String motivo) {
    }

    private final SolicitudService solicitudes;

    public SolicitudController(SolicitudService solicitudes) {
        this.solicitudes = solicitudes;
    }

    @GetMapping
    public List<SolicitudResultado> listar(@RequestParam(required = false) String estado) {
        return solicitudes.listar(estado);
    }

    // Sin login en el esqueleto: el administrador llega en el cuerpo (autenticación fuera de alcance).
    @PostMapping("/{id}/aprobar")
    public SolicitudResultado aprobar(@PathVariable String id, @Valid @RequestBody Aprobar dto) {
        return solicitudes.aprobar(id, dto.administrador());
    }

    @PostMapping("/{id}/rechazar")
    public SolicitudResultado rechazar(@PathVariable String id, @Valid @RequestBody Rechazar dto) {
        return solicitudes.rechazar(id, dto.administrador(), dto.motivo());
    }
}
