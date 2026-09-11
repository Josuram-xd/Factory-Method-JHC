package com.globaldocs.controller;

import com.globaldocs.model.BatchResult;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.DocumentType;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.service.BatchProcessingService;
import jakarta.validation.Valid;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/documents")
@CrossOrigin(origins = "*")
public class DocumentController {

    private final BatchProcessingService batchProcessingService;

    public DocumentController(BatchProcessingService batchProcessingService) {
        this.batchProcessingService = batchProcessingService;
    }

    @PostMapping("/process")
    public ProcessingResult processOne(@Valid @RequestBody DocumentRequest request) {
        return batchProcessingService.processOne(request);
    }

    @PostMapping("/batch")
    public BatchResult processBatch(@Valid @RequestBody List<DocumentRequest> requests) {
        return batchProcessingService.processBatch(requests);
    }

    @GetMapping("/metadata")
    public Map<String, Object> metadata() {
        return Map.of(
                "countries", Country.values(),
                "documentTypes", DocumentType.values(),
                "formats", DocumentFormat.values()
        );
    }
}
