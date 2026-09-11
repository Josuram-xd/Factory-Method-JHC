package com.globaldocs.extraction;

import com.globaldocs.model.DocumentFormat;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

/** Extrae texto de documentos Word binarios .doc (formato 97-2003) con Apache POI. */
@Component
public class WordContentExtractor implements ContentExtractor {

    @Override
    public Set<DocumentFormat> supportedFormats() {
        return EnumSet.of(DocumentFormat.DOC);
    }

    @Override
    public String extract(MultipartFile file) throws IOException {
        try (HWPFDocument document = new HWPFDocument(file.getInputStream());
             WordExtractor extractor = new WordExtractor(document)) {
            return extractor.getText();
        }
    }
}
