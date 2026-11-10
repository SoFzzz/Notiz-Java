package com.notiz.calificaciones.negocio;

/** Se viola una regla de negocio (se traduce a HTTP 400). */
public class ReglaNegocioException extends RuntimeException {
    public ReglaNegocioException(String mensaje) {
        super(mensaje);
    }
}
