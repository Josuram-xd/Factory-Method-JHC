package com.globaldocs.model;

import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Metadatos de un documento a procesar. El archivo real se sube por separado
 * (multipart) y el servidor completa fileName/content a partir de él; "fields"
 * transporta los metadatos regulatorios que el emisor declara (NIT, RFC, CUIT,
 * RUT, etc.), que luego cada país valida contra sus propias reglas.
 */
public class DocumentRequest {

    private String id = UUID.randomUUID().toString();

    private String fileName;

    @NotNull
    private Country country;

    @NotNull
    private DocumentType documentType;

    @NotNull
    private DocumentFormat format;

    private String content = "";

    private Map<String, String> fields = new HashMap<>();

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public Country getCountry() {
        return country;
    }

    public void setCountry(Country country) {
        this.country = country;
    }

    public DocumentType getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DocumentType documentType) {
        this.documentType = documentType;
    }

    public DocumentFormat getFormat() {
        return format;
    }

    public void setFormat(DocumentFormat format) {
        this.format = format;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Map<String, String> getFields() {
        return fields;
    }

    public void setFields(Map<String, String> fields) {
        this.fields = fields;
    }
}
