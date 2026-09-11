package com.globaldocs.exception;

/** Se lanza cuando un documento no cumple el formato o la regulación del país. */
public class DocumentValidationException extends RuntimeException {
    public DocumentValidationException(String message) {
        super(message);
    }
}
