package com.globaldocs.processor;

import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.exception.DocumentValidationException;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.DocumentType;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.model.ProcessingStatus;

import java.util.EnumMap;
import java.util.EnumSet;
import java.util.Map;
import java.util.Set;

/**
 * "Product" del patrón Factory Method. Cada país implementa sus propias
 * reglas de validación regulatoria (validateCountryRules) y de extracción,
 * mientras que el flujo general de procesamiento (process) es un template
 * method compartido por todos los países.
 */
public abstract class DocumentProcessor {

    private static final Map<DocumentType, Set<DocumentFormat>> ALLOWED_FORMATS = new EnumMap<>(DocumentType.class);

    static {
        ALLOWED_FORMATS.put(DocumentType.FACTURA_ELECTRONICA, EnumSet.of(DocumentFormat.PDF, DocumentFormat.CSV));
        ALLOWED_FORMATS.put(DocumentType.CONTRATO_LEGAL, EnumSet.of(DocumentFormat.PDF, DocumentFormat.DOC, DocumentFormat.MD));
        ALLOWED_FORMATS.put(DocumentType.REPORTE_FINANCIERO, EnumSet.of(DocumentFormat.XLSX, DocumentFormat.CSV, DocumentFormat.PDF));
        ALLOWED_FORMATS.put(DocumentType.CERTIFICADO_DIGITAL, EnumSet.of(DocumentFormat.PDF, DocumentFormat.TXT));
        ALLOWED_FORMATS.put(DocumentType.DECLARACION_TRIBUTARIA, EnumSet.of(DocumentFormat.PDF, DocumentFormat.XLSX, DocumentFormat.CSV));
    }

    /** Template method: orquesta validación de formato, validación regulatoria y extracción. */
    public final ProcessingResult process(DocumentRequest request) {
        try {
            validateFormat(request);
            validateCountryRules(request);
            String summary = extractContent(request);
            return ProcessingResult.success(request, summary);
        } catch (DocumentValidationException e) {
            return ProcessingResult.failure(request, ProcessingStatus.VALIDATION_ERROR, e.getMessage());
        } catch (DocumentProcessingException e) {
            return ProcessingResult.failure(request, ProcessingStatus.PROCESSING_ERROR, e.getMessage());
        } catch (Exception e) {
            return ProcessingResult.failure(request, ProcessingStatus.PROCESSING_ERROR,
                    "Error inesperado procesando el documento: " + e.getMessage());
        }
    }

    private void validateFormat(DocumentRequest request) {
        Set<DocumentFormat> allowed = ALLOWED_FORMATS.get(request.getDocumentType());
        if (allowed == null || !allowed.contains(request.getFormat())) {
            throw new DocumentValidationException(String.format(
                    "El formato %s no está permitido para el tipo de documento %s",
                    request.getFormat(), request.getDocumentType().getDisplayName()));
        }
    }

    protected String requireField(DocumentRequest request, String key, String humanName) {
        String value = request.getFields().get(key);
        if (value == null || value.isBlank()) {
            throw new DocumentValidationException(
                    "Falta el campo obligatorio '" + humanName + "' para " + getCountry().getDisplayName());
        }
        return value;
    }

    protected void requireMatch(String value, String regex, String humanName) {
        if (!value.matches(regex)) {
            throw new DocumentValidationException(
                    humanName + " no cumple el formato exigido por " + getCountry().getDisplayName() + ": '" + value + "'");
        }
    }

    /** Reglas de validación específicas del país y del tipo de documento. */
    protected abstract void validateCountryRules(DocumentRequest request);

    /** Simula la extracción/lectura del contenido del documento ya validado. */
    protected String extractContent(DocumentRequest request) {
        if (request.getContent() != null && request.getContent().toUpperCase().contains("ERROR")) {
            throw new DocumentProcessingException("El motor de extracción encontró contenido corrupto o ilegible");
        }
        return "Documento '" + request.getFileName() + "' procesado correctamente para "
                + getCountry().getDisplayName() + " (" + request.getDocumentType().getDisplayName() + ")";
    }

    public abstract Country getCountry();
}
