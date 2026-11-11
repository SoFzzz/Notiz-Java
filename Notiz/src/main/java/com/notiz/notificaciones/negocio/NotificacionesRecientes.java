package com.notiz.notificaciones.negocio;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import org.springframework.stereotype.Component;

/** Registro en memoria de las últimas notificaciones (acotado). Se pierde al reiniciar: solo sirve para la demostración. */
@Component
public class NotificacionesRecientes {

    static final int CAPACIDAD = 50;

    private final List<NotificacionRegistrada> entradas = new ArrayList<>();

    public synchronized void registrar(NotificacionRegistrada entrada) {
        entradas.add(entrada);
        if (entradas.size() > CAPACIDAD) {
            entradas.remove(0);
        }
    }

    /** Las más recientes primero. */
    public synchronized List<NotificacionRegistrada> recientes() {
        List<NotificacionRegistrada> copia = new ArrayList<>(entradas);
        Collections.reverse(copia);
        return copia;
    }
}
