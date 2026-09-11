package com.globaldocs.exception;

/** Se lanza cuando no existe una fábrica registrada para el país solicitado. */
public class UnsupportedCountryException extends RuntimeException {
    public UnsupportedCountryException(String message) {
        super(message);
    }
}
