# GlobalDocs Solutions

Sistema de procesamiento de documentos empresariales para una operación multinacional
(Colombia, México, Argentina, Chile), construido para el taller de **Factory Method**.

## Arquitectura y patrón aplicado

El patrón **Factory Method** resuelve el requerimiento de "validar por país": cada país
tiene su propia subclase de `DocumentProcessor` con las reglas regulatorias simuladas
(NIT/CUFE en Colombia, RFC/CFDI en México, CUIT/CAE en Argentina, RUT/DTE en Chile), y una
fábrica concreta por país decide qué procesador instanciar.

```
backend/src/main/java/com/globaldocs/
├── model/        Country, DocumentType, DocumentFormat, DocumentRequest, ProcessingResult, BatchResult...
├── processor/    DocumentProcessor (Product abstracto, template method) + 4 implementaciones por país
├── factory/      DocumentProcessorFactory (Creator abstracto) + 4 fábricas + registro (Provider)
├── extraction/   ContentExtractor por formato (PDF/XLSX/DOC/TXT-MD-CSV) + registro (Strategy)
├── service/      BatchProcessingService (lee el archivo real, extrae texto y delega en el Factory Method)
├── exception/    Excepciones de dominio + manejador global de errores (@RestControllerAdvice)
└── controller/   API REST (DocumentController)
```

- **Product** (`DocumentProcessor`): define el flujo común (`process`) como *template method*:
  valida formato → valida reglas del país → extrae contenido. Las subclases solo implementan
  `validateCountryRules` y pueden sobreescribir la extracción.
- **Creator** (`DocumentProcessorFactory`): declara el *factory method* `createProcessor()`.
  Cada país (`ColombiaProcessorFactory`, `MexicoProcessorFactory`, ...) decide qué
  `DocumentProcessor` concreto crear.
- **DocumentProcessorFactoryProvider**: Spring inyecta todas las fábricas registradas y las
  indexa por país, así el resto de la app pide "la fábrica de Chile" sin conocer la clase concreta.
- **Manejo de errores**: cada documento del lote se procesa de forma aislada — un
  `DocumentValidationException` (dato inválido) o `DocumentProcessingException` (fallo de
  extracción) no interrumpe el resto del lote; se reporta como `VALIDATION_ERROR` o
  `PROCESSING_ERROR` en el resultado.
- **Procesamiento por lotes**: `POST /api/documents/batch` recibe archivos reales y sus metadatos,
  y devuelve un `BatchResult` con totales, éxitos/fallos y desglose por país y tipo de documento.
- **Extracción real de contenido** (`extraction/`, patrón Strategy): cada formato tiene su propio
  extractor — `PdfContentExtractor` (Apache PDFBox), `ExcelContentExtractor` (.xlsx, Apache POI),
  `WordContentExtractor` (.doc binario, Apache POI HWPF) y `PlainTextContentExtractor`
  (.txt/.md/.csv). `ContentExtractorRegistry` los indexa por `DocumentFormat`, igual que el
  `DocumentProcessorFactoryProvider` indexa las fábricas por país. El servicio valida que la
  extensión real del archivo coincida con el formato declarado antes de extraer.

## Backend (Java 17 + Spring Boot 3)

```bash
cd backend
mvn spring-boot:run       # http://localhost:8080
mvn test                  # pruebas unitarias del Factory Method
```

Endpoints (todos `multipart/form-data`, salvo `/metadata`):

| Método | Ruta                       | Partes del request                                              |
|--------|----------------------------|-------------------------------------------------------------------|
| POST   | `/api/documents/process`   | `file` (el archivo) + `metadata` (JSON: country, documentType, format, fields) |
| POST   | `/api/documents/batch`     | `files` (uno o más archivos) + `metadataList` (JSON, arreglo pareado por orden) |
| GET    | `/api/documents/metadata`  | — Lista países, tipos de documento y formatos                      |

Ejemplo con `curl`:

```bash
curl -X POST http://localhost:8080/api/documents/batch \
  -F "files=@factura.csv;type=text/csv" \
  -F 'metadataList=[{"country":"COLOMBIA","documentType":"FACTURA_ELECTRONICA","format":"CSV","fields":{"nit":"900123456-7","cufe":"'"$(printf 'a%.0s' {1..96})"'"}}];type=application/json'
```

## Frontend (React + Vite)

```bash
cd frontend
npm install
npm run dev                # http://localhost:5173 (proxy /api -> :8080)
```

Dashboard para armar un lote de documentos (con campos regulatorios dinámicos según país y
tipo), enviarlo a procesar y ver el resumen (totales, tasa de éxito, desglose por país) y el
detalle de resultados por documento.
