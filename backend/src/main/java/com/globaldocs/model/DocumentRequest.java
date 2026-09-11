package com.globaldocs.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Representa un documento a procesar. El contenido real del archivo no se
 * transporta en este taller; en su lugar "fields" simula los metadatos
 * regulatorios que un parser real extraería (NIT, RFC, CUIT, RUT, etc.).
 */
public class DocumentRequest {

    private String id = UUID.randomUUID().toString();

    @NotBlank
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
