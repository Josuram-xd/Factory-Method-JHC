package com.globaldocs.processor;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentRequest;

/**
 * Reglas simuladas de SAT (México):
 * - Factura Electrónica: RFC + UUID de CFDI
 * - Contrato Legal: número de folio notarial
 * - Reporte Financiero: RFC de la entidad
 * - Certificado Digital: número de serie del CSD/e.firma
 * - Declaración Tributaria: RFC + ejercicio fiscal
 */
public class MexicoDocumentProcessor extends DocumentProcessor {

    private static final String RFC_REGEX = "[A-ZÑ&]{3,4}\\d{6}[A-Z0-9]{3}";
    private static final String UUID_REGEX =
            "[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}";

    @Override
    protected void validateCountryRules(DocumentRequest request) {
        switch (request.getDocumentType()) {
            case FACTURA_ELECTRONICA -> {
                String rfc = requireField(request, "rfc", "RFC");
                requireMatch(rfc, RFC_REGEX, "RFC");
                String uuid = requireField(request, "uuidCfdi", "UUID del CFDI");
                requireMatch(uuid, UUID_REGEX, "UUID del CFDI");
            }
            case CONTRATO_LEGAL -> {
                String folio = requireField(request, "folioNotarial", "Folio notarial");
                requireMatch(folio, "\\d{3,6}", "Folio notarial");
            }
            case REPORTE_FINANCIERO -> {
                String rfc = requireField(request, "rfc", "RFC");
                requireMatch(rfc, RFC_REGEX, "RFC");
            }
            case CERTIFICADO_DIGITAL -> {
                String serie = requireField(request, "numeroSerieCsd", "Número de serie del CSD");
                requireMatch(serie, "[A-Za-z0-9]{8,40}", "Número de serie del CSD");
            }
            case DECLARACION_TRIBUTARIA -> {
                String rfc = requireField(request, "rfc", "RFC");
                requireMatch(rfc, RFC_REGEX, "RFC");
                String ejercicio = requireField(request, "ejercicioFiscal", "Ejercicio fiscal");
                requireMatch(ejercicio, "20\\d{2}", "Ejercicio fiscal");
            }
        }
    }

    @Override
    public Country getCountry() {
        return Country.MEXICO;
    }
}
