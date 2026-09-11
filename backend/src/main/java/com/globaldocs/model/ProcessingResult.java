package com.globaldocs.model;

import java.time.Instant;

public class ProcessingResult {

    private String documentId;
    private String fileName;
    private Country country;
    private DocumentType documentType;
    private ProcessingStatus status;
    private String message;
    private Instant processedAt = Instant.now();

    public static ProcessingResult success(DocumentRequest request, String message) {
        return build(request, ProcessingStatus.SUCCESS, message);
    }

    public static ProcessingResult failure(DocumentRequest request, ProcessingStatus status, String message) {
        return build(request, status, message);
    }

    private static ProcessingResult build(DocumentRequest request, ProcessingStatus status, String message) {
        ProcessingResult result = new ProcessingResult();
        result.documentId = request.getId();
        result.fileName = request.getFileName();
        result.country = request.getCountry();
        result.documentType = request.getDocumentType();
        result.status = status;
        result.message = message;
        return result;
    }

    public String getDocumentId() {
        return documentId;
    }

    public String getFileName() {
        return fileName;
    }

    public Country getCountry() {
        return country;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public ProcessingStatus getStatus() {
        return status;
    }

    public String getMessage() {
        return message;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }
}
