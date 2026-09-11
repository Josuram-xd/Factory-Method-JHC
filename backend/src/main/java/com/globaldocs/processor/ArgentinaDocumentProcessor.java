package com.globaldocs.processor;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentRequest;

/**
 * Reglas simuladas de AFIP (Argentina):
 * - Factura Electrónica: CUIT + CAE
 * - Contrato Legal: número de escritura del colegio de escribanos
 * - Reporte Financiero: CUIT de la entidad
 * - Certificado Digital: número de serie del certificado
 * - Declaración Tributaria: CUIT + período fiscal
 */
public class ArgentinaDocumentProcessor extends DocumentProcessor {

    private static final String CUIT_REGEX = "\\d{2}-\\d{8}-\\d";

    @Override
    protected void validateCountryRules(DocumentRequest request) {
        switch (request.getDocumentType()) {
            case FACTURA_ELECTRONICA -> {
                String cuit = requireField(request, "cuit", "CUIT");
                requireMatch(cuit, CUIT_REGEX, "CUIT");
                String cae = requireField(request, "cae", "CAE");
                requireMatch(cae, "\\d{14}", "CAE");
            }
            case CONTRATO_LEGAL -> {
                String escritura = requireField(request, "numeroEscritura", "Número de escritura");
                requireMatch(escritura, "\\d{3,6}", "Número de escritura");
            }
            case REPORTE_FINANCIERO -> {
                String cuit = requireField(request, "cuit", "CUIT");
                requireMatch(cuit, CUIT_REGEX, "CUIT");
            }
            case CERTIFICADO_DIGITAL -> {
                String serie = requireField(request, "numeroSerie", "Número de serie del certificado");
                requireMatch(serie, "[A-Za-z0-9]{8,40}", "Número de serie del certificado");
            }
            case DECLARACION_TRIBUTARIA -> {
                String cuit = requireField(request, "cuit", "CUIT");
                requireMatch(cuit, CUIT_REGEX, "CUIT");
                String periodo = requireField(request, "periodoFiscal", "Período fiscal");
                requireMatch(periodo, "20\\d{2}-(0[1-9]|1[0-2])", "Período fiscal");
            }
        }
    }

    @Override
    public Country getCountry() {
        return Country.ARGENTINA;
    }
}
