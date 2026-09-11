package com.globaldocs.controller;

import com.globaldocs.model.BatchResult;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.DocumentType;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.service.BatchProcessingService;
import jakarta.validation.Valid;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

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

    @PostMapping(value = "/process", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ProcessingResult processOne(@RequestPart("file") MultipartFile file,
                                        @RequestPart("metadata") @Valid DocumentRequest metadata) {
        return batchProcessingService.processOne(file, metadata);
    }

    @PostMapping(value = "/batch", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public BatchResult processBatch(@RequestPart("files") List<MultipartFile> files,
                                     @RequestPart("metadataList") List<DocumentRequest> metadataList) {
        return batchProcessingService.processBatch(files, metadataList);
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
