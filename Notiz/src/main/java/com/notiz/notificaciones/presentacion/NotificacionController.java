package com.notiz.notificaciones.presentacion;

import com.notiz.notificaciones.negocio.NotificacionRegistrada;
import com.notiz.notificaciones.negocio.NotificacionesRecientes;
import com.notiz.notificaciones.negocio.SimulacionFallo;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * Consulta del registro reciente de notificaciones y control de demostración del fallo simulado.
 * El control de fallo (/simular-fallo) existe solo para la demo: no es parte del dominio.
 */
@RestController
@RequestMapping("/api/notificaciones")
public class NotificacionController {

    public record EstadoFallo(boolean activo) {
    }

    public record CambiarFallo(@NotNull Boolean activo) {
    }

    private final NotificacionesRecientes recientes;
    private final SimulacionFallo simulacion;

    public NotificacionController(NotificacionesRecientes recientes, SimulacionFallo simulacion) {
        this.recientes = recientes;
        this.simulacion = simulacion;
    }

    @GetMapping
    public List<NotificacionRegistrada> recientes() {
        return recientes.recientes();
    }

    @GetMapping("/simular-fallo")
    public EstadoFallo estadoFallo() {
        return new EstadoFallo(simulacion.activo());
    }

    @PutMapping("/simular-fallo")
    public EstadoFallo cambiarFallo(@Valid @RequestBody CambiarFallo dto) {
        simulacion.activar(dto.activo());
        return new EstadoFallo(simulacion.activo());
    }
}
