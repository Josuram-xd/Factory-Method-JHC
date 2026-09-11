export const COUNTRIES = [
  { value: 'COLOMBIA', label: 'Colombia', flag: '🇨🇴' },
  { value: 'MEXICO', label: 'México', flag: '🇲🇽' },
  { value: 'ARGENTINA', label: 'Argentina', flag: '🇦🇷' },
  { value: 'CHILE', label: 'Chile', flag: '🇨🇱' },
]

export const DOCUMENT_TYPES = [
  { value: 'FACTURA_ELECTRONICA', label: 'Factura Electrónica' },
  { value: 'CONTRATO_LEGAL', label: 'Contrato Legal' },
  { value: 'REPORTE_FINANCIERO', label: 'Reporte Financiero' },
  { value: 'CERTIFICADO_DIGITAL', label: 'Certificado Digital' },
  { value: 'DECLARACION_TRIBUTARIA', label: 'Declaración Tributaria' },
]

export const ALLOWED_FORMATS = {
  FACTURA_ELECTRONICA: ['PDF', 'CSV'],
  CONTRATO_LEGAL: ['PDF', 'DOC', 'MD'],
  REPORTE_FINANCIERO: ['XLSX', 'CSV', 'PDF'],
  CERTIFICADO_DIGITAL: ['PDF', 'TXT'],
  DECLARACION_TRIBUTARIA: ['PDF', 'XLSX', 'CSV'],
}

// Campos regulatorios simulados que cada país exige por tipo de documento.
export const COUNTRY_FIELDS = {
  COLOMBIA: {
    FACTURA_ELECTRONICA: [
      { key: 'nit', label: 'NIT', placeholder: '900123456-7' },
      { key: 'cufe', label: 'CUFE (96 hex)', placeholder: 'a'.repeat(96) },
    ],
    CONTRATO_LEGAL: [{ key: 'numeroEscritura', label: 'Número de escritura', placeholder: '4521' }],
    REPORTE_FINANCIERO: [{ key: 'nit', label: 'NIT', placeholder: '900123456-7' }],
    CERTIFICADO_DIGITAL: [{ key: 'numeroSerie', label: 'Número de serie', placeholder: 'CD8F21AA0012' }],
    DECLARACION_TRIBUTARIA: [
      { key: 'nit', label: 'NIT', placeholder: '900123456-7' },
      { key: 'codigoFormularioDian', label: 'Código formulario DIAN', placeholder: '110' },
    ],
  },
  MEXICO: {
    FACTURA_ELECTRONICA: [
      { key: 'rfc', label: 'RFC', placeholder: 'ABC123456T01' },
      { key: 'uuidCfdi', label: 'UUID CFDI', placeholder: '3a1f9c2e-1b4d-4a6f-9c2e-1b4d4a6f9c2e' },
    ],
    CONTRATO_LEGAL: [{ key: 'folioNotarial', label: 'Folio notarial', placeholder: '18234' }],
    REPORTE_FINANCIERO: [{ key: 'rfc', label: 'RFC', placeholder: 'ABC123456T01' }],
    CERTIFICADO_DIGITAL: [{ key: 'numeroSerieCsd', label: 'Número de serie CSD', placeholder: '00001000000701234567' }],
    DECLARACION_TRIBUTARIA: [
      { key: 'rfc', label: 'RFC', placeholder: 'ABC123456T01' },
      { key: 'ejercicioFiscal', label: 'Ejercicio fiscal', placeholder: '2024' },
    ],
  },
  ARGENTINA: {
    FACTURA_ELECTRONICA: [
      { key: 'cuit', label: 'CUIT', placeholder: '30-71234567-4' },
      { key: 'cae', label: 'CAE', placeholder: '74123456789012' },
    ],
    CONTRATO_LEGAL: [{ key: 'numeroEscritura', label: 'Número de escritura', placeholder: '7231' }],
    REPORTE_FINANCIERO: [{ key: 'cuit', label: 'CUIT', placeholder: '30-71234567-4' }],
    CERTIFICADO_DIGITAL: [{ key: 'numeroSerie', label: 'Número de serie', placeholder: 'AR0099231B' }],
    DECLARACION_TRIBUTARIA: [
      { key: 'cuit', label: 'CUIT', placeholder: '30-71234567-4' },
      { key: 'periodoFiscal', label: 'Período fiscal (AAAA-MM)', placeholder: '2024-05' },
    ],
  },
  CHILE: {
    FACTURA_ELECTRONICA: [
      { key: 'rut', label: 'RUT emisor', placeholder: '76.123.456-8' },
      { key: 'folioDte', label: 'Folio DTE', placeholder: '10245' },
    ],
    CONTRATO_LEGAL: [{ key: 'numeroRepertorio', label: 'Número de repertorio', placeholder: '3312' }],
    REPORTE_FINANCIERO: [{ key: 'rut', label: 'RUT', placeholder: '76.123.456-8' }],
    CERTIFICADO_DIGITAL: [{ key: 'numeroSerie', label: 'Número de serie', placeholder: 'CL77213F' }],
    DECLARACION_TRIBUTARIA: [
      { key: 'rut', label: 'RUT', placeholder: '76.123.456-8' },
      { key: 'codigoFormularioSii', label: 'Código formulario SII', placeholder: 'F29' },
    ],
  },
}

export const COUNTRY_LABEL = Object.fromEntries(COUNTRIES.map((c) => [c.value, c]))
export const DOCUMENT_TYPE_LABEL = Object.fromEntries(DOCUMENT_TYPES.map((t) => [t.value, t.label]))
