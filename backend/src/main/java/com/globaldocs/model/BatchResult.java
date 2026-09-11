package com.globaldocs.model;

import java.time.Instant;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class BatchResult {

    private final String batchId = UUID.randomUUID().toString();
    private final Instant processedAt = Instant.now();
    private int total;
    private int successful;
    private int failed;
    private Map<Country, Long> byCountry = new EnumMap<>(Country.class);
    private Map<DocumentType, Long> byDocumentType = new EnumMap<>(DocumentType.class);
    private List<ProcessingResult> results;

    public static BatchResult of(List<ProcessingResult> results) {
        BatchResult batch = new BatchResult();
        batch.results = results;
        batch.total = results.size();
        batch.successful = (int) results.stream().filter(r -> r.getStatus() == ProcessingStatus.SUCCESS).count();
        batch.failed = batch.total - batch.successful;
        for (ProcessingResult r : results) {
            batch.byCountry.merge(r.getCountry(), 1L, Long::sum);
            batch.byDocumentType.merge(r.getDocumentType(), 1L, Long::sum);
        }
        return batch;
    }

    public String getBatchId() {
        return batchId;
    }

    public Instant getProcessedAt() {
        return processedAt;
    }

    public int getTotal() {
        return total;
    }

    public int getSuccessful() {
        return successful;
    }

    public int getFailed() {
        return failed;
    }

    public Map<Country, Long> getByCountry() {
        return byCountry;
    }

    public Map<DocumentType, Long> getByDocumentType() {
        return byDocumentType;
    }

    public List<ProcessingResult> getResults() {
        return results;
    }
}
