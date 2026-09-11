package com.globaldocs.processor;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentRequest;

/**
 * Reglas simuladas del SII (Chile):
 * - Factura Electrónica: RUT emisor + folio del DTE
 * - Contrato Legal: repertorio notarial
 * - Reporte Financiero: RUT de la entidad
 * - Certificado Digital: número de serie del certificado
 * - Declaración Tributaria: RUT + código de formulario SII (ej. F29)
 */
public class ChileDocumentProcessor extends DocumentProcessor {

    private static final String RUT_REGEX = "\\d{1,2}\\.\\d{3}\\.\\d{3}-[0-9kK]";

    @Override
    protected void validateCountryRules(DocumentRequest request) {
        switch (request.getDocumentType()) {
            case FACTURA_ELECTRONICA -> {
                String rut = requireField(request, "rut", "RUT");
                requireMatch(rut, RUT_REGEX, "RUT");
                String folio = requireField(request, "folioDte", "Folio del DTE");
                requireMatch(folio, "\\d{1,10}", "Folio del DTE");
            }
            case CONTRATO_LEGAL -> {
                String repertorio = requireField(request, "numeroRepertorio", "Número de repertorio notarial");
                requireMatch(repertorio, "\\d{3,6}", "Número de repertorio notarial");
            }
            case REPORTE_FINANCIERO -> {
                String rut = requireField(request, "rut", "RUT");
                requireMatch(rut, RUT_REGEX, "RUT");
            }
            case CERTIFICADO_DIGITAL -> {
                String serie = requireField(request, "numeroSerie", "Número de serie del certificado");
                requireMatch(serie, "[A-Za-z0-9]{8,40}", "Número de serie del certificado");
            }
            case DECLARACION_TRIBUTARIA -> {
                String rut = requireField(request, "rut", "RUT");
                requireMatch(rut, RUT_REGEX, "RUT");
                String formulario = requireField(request, "codigoFormularioSii", "Código de formulario SII");
                requireMatch(formulario, "F\\d{2}", "Código de formulario SII");
            }
        }
    }

    @Override
    public Country getCountry() {
        return Country.CHILE;
    }
}
