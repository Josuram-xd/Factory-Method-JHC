package com.globaldocs.processor;

import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentRequest;

/**
 * Reglas simuladas de DIAN (Colombia):
 * - Factura Electrónica: NIT + CUFE (96 caracteres hexadecimales)
 * - Contrato Legal: número de escritura / notaría
 * - Reporte Financiero: NIT de la entidad reportante
 * - Certificado Digital: número de serie del certificado
 * - Declaración Tributaria: NIT + código de formulario DIAN
 */
public class ColombiaDocumentProcessor extends DocumentProcessor {

    @Override
    protected void validateCountryRules(DocumentRequest request) {
        switch (request.getDocumentType()) {
            case FACTURA_ELECTRONICA -> {
                String nit = requireField(request, "nit", "NIT");
                requireMatch(nit, "\\d{9}-\\d", "NIT");
                String cufe = requireField(request, "cufe", "CUFE");
                requireMatch(cufe, "[a-fA-F0-9]{96}", "CUFE");
            }
            case CONTRATO_LEGAL -> {
                String escritura = requireField(request, "numeroEscritura", "Número de escritura");
                requireMatch(escritura, "\\d{3,6}", "Número de escritura");
            }
            case REPORTE_FINANCIERO -> {
                String nit = requireField(request, "nit", "NIT");
                requireMatch(nit, "\\d{9}-\\d", "NIT");
            }
            case CERTIFICADO_DIGITAL -> {
                String serie = requireField(request, "numeroSerie", "Número de serie del certificado");
                requireMatch(serie, "[A-Za-z0-9]{8,40}", "Número de serie del certificado");
            }
            case DECLARACION_TRIBUTARIA -> {
                String nit = requireField(request, "nit", "NIT");
                requireMatch(nit, "\\d{9}-\\d", "NIT");
                String formulario = requireField(request, "codigoFormularioDian", "Código de formulario DIAN");
                requireMatch(formulario, "\\d{3}", "Código de formulario DIAN");
            }
        }
    }

    @Override
    public Country getCountry() {
        return Country.COLOMBIA;
    }
}
