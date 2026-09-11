package com.globaldocs.service;

import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.exception.DocumentValidationException;
import com.globaldocs.extraction.ContentExtractorRegistry;
import com.globaldocs.factory.DocumentProcessorFactory;
import com.globaldocs.factory.DocumentProcessorFactoryProvider;
import com.globaldocs.model.BatchResult;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.model.ProcessingStatus;
import com.globaldocs.processor.DocumentProcessor;
import com.globaldocs.util.FormatUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchProcessingService {

    private final DocumentProcessorFactoryProvider factoryProvider;
    private final ContentExtractorRegistry extractorRegistry;

    public BatchProcessingService(DocumentProcessorFactoryProvider factoryProvider,
                                   ContentExtractorRegistry extractorRegistry) {
        this.factoryProvider = factoryProvider;
        this.extractorRegistry = extractorRegistry;
    }

    /**
     * Procesa un único documento: lee y valida el archivo real subido,
     * extrae su contenido según el formato y luego aplica las reglas
     * regulatorias del país mediante el procesador que crea la fábrica.
     */
    public ProcessingResult processOne(MultipartFile file, DocumentRequest metadata) {
        try {
            attachExtractedContent(file, metadata);
        } catch (DocumentValidationException e) {
            return ProcessingResult.failure(metadata, ProcessingStatus.VALIDATION_ERROR, e.getMessage());
        } catch (DocumentProcessingException e) {
            return ProcessingResult.failure(metadata, ProcessingStatus.PROCESSING_ERROR, e.getMessage());
        } catch (Exception e) {
            return ProcessingResult.failure(metadata, ProcessingStatus.PROCESSING_ERROR,
                    "No se pudo leer el archivo '" + metadata.getFileName() + "': " + e.getMessage());
        }

        DocumentProcessorFactory factory = factoryProvider.getFactory(metadata.getCountry());
        DocumentProcessor processor = factory.createProcessor();
        return processor.process(metadata);
    }

    private void attachExtractedContent(MultipartFile file, DocumentRequest metadata) throws Exception {
        if (file == null || file.isEmpty()) {
            throw new DocumentValidationException("Debes adjuntar un archivo para '" + metadata.getFileName() + "'");
        }
        metadata.setFileName(file.getOriginalFilename());

        DocumentFormat actualFormat = FormatUtils.fromFilename(file.getOriginalFilename())
                .orElseThrow(() -> new DocumentValidationException(
                        "No se reconoce la extensión del archivo: " + file.getOriginalFilename()));

        if (metadata.getFormat() != actualFormat) {
            throw new DocumentValidationException(String.format(
                    "El formato declarado (%s) no coincide con la extensión real del archivo (%s)",
                    metadata.getFormat(), actualFormat));
        }

        String extractedText = extractorRegistry.get(actualFormat).extract(file);
        metadata.setContent(extractedText);
    }

    /**
     * Procesa un lote de documentos. Cada documento se procesa de forma
     * independiente: el fallo de uno (validación o error inesperado) no
     * interrumpe el procesamiento del resto del lote.
     */
    public BatchResult processBatch(List<MultipartFile> files, List<DocumentRequest> metadataList) {
        if (files.size() != metadataList.size()) {
            throw new DocumentValidationException(
                    "La cantidad de archivos (" + files.size() + ") no coincide con la cantidad de metadatos (" + metadataList.size() + ")");
        }
        List<ProcessingResult> results = new ArrayList<>(files.size());
        for (int i = 0; i < files.size(); i++) {
            results.add(processOne(files.get(i), metadataList.get(i)));
        }
        return BatchResult.of(results);
    }
}
