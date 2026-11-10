package com.notiz.calificaciones.negocio;

/** El recurso pedido no existe (se traduce a HTTP 404). */
public class NoEncontradoException extends RuntimeException {
    public NoEncontradoException(String mensaje) {
        super(mensaje);
    }
}
