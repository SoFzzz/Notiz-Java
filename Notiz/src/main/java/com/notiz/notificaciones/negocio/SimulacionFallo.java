package com.notiz.notificaciones.negocio;

import java.util.concurrent.atomic.AtomicBoolean;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/** Interruptor de fallo simulado: arranca con la propiedad notiz.notificaciones.simular-fallo y se puede cambiar en caliente. */
@Component
public class SimulacionFallo {

    private final AtomicBoolean activo;

    public SimulacionFallo(@Value("${notiz.notificaciones.simular-fallo:false}") boolean inicial) {
        this.activo = new AtomicBoolean(inicial);
    }

    public boolean activo() {
        return activo.get();
    }

    public void activar(boolean valor) {
        activo.set(valor);
    }
}
