package com.globaldocs.service;

import com.globaldocs.factory.DocumentProcessorFactory;
import com.globaldocs.factory.DocumentProcessorFactoryProvider;
import com.globaldocs.model.BatchResult;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.processor.DocumentProcessor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class BatchProcessingService {

    private final DocumentProcessorFactoryProvider factoryProvider;

    public BatchProcessingService(DocumentProcessorFactoryProvider factoryProvider) {
        this.factoryProvider = factoryProvider;
    }

    public ProcessingResult processOne(DocumentRequest request) {
        DocumentProcessorFactory factory = factoryProvider.getFactory(request.getCountry());
        DocumentProcessor processor = factory.createProcessor();
        return processor.process(request);
    }

    /**
     * Procesa un lote de documentos. Cada documento se procesa de forma
     * independiente: el fallo de uno (validación o error inesperado) no
     * interrumpe el procesamiento del resto del lote.
     */
    public BatchResult processBatch(List<DocumentRequest> requests) {
        List<ProcessingResult> results = new ArrayList<>(requests.size());
        for (DocumentRequest request : requests) {
            results.add(processOne(request));
        }
        return BatchResult.of(results);
    }
}
