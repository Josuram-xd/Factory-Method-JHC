package com.globaldocs.exception;

/** Se lanza cuando ocurre un error inesperado durante el procesamiento (no de validación). */
public class DocumentProcessingException extends RuntimeException {
    public DocumentProcessingException(String message) {
        super(message);
    }

    public DocumentProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
