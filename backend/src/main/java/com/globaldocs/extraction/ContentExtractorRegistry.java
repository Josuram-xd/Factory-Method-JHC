package com.globaldocs.extraction;

import com.globaldocs.exception.DocumentProcessingException;
import com.globaldocs.model.DocumentFormat;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Registro de extractores por formato. Spring inyecta todos los beans
 * {@link ContentExtractor} y este componente los indexa por cada
 * {@link DocumentFormat} que declaran soportar.
 */
@Component
public class ContentExtractorRegistry {

    private final Map<DocumentFormat, ContentExtractor> extractors = new EnumMap<>(DocumentFormat.class);

    public ContentExtractorRegistry(List<ContentExtractor> registeredExtractors) {
        for (ContentExtractor extractor : registeredExtractors) {
            for (DocumentFormat format : extractor.supportedFormats()) {
                extractors.put(format, extractor);
            }
        }
    }

    public ContentExtractor get(DocumentFormat format) {
        ContentExtractor extractor = extractors.get(format);
        if (extractor == null) {
            throw new DocumentProcessingException("No hay un extractor de contenido configurado para el formato " + format);
        }
        return extractor;
    }
}
