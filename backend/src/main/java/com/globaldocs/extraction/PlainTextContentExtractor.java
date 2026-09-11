package com.globaldocs.extraction;

import com.globaldocs.model.DocumentFormat;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.EnumSet;
import java.util.Set;

/** Lee formatos de texto plano tal cual: .txt, .md y .csv. */
@Component
public class PlainTextContentExtractor implements ContentExtractor {

    @Override
    public Set<DocumentFormat> supportedFormats() {
        return EnumSet.of(DocumentFormat.TXT, DocumentFormat.MD, DocumentFormat.CSV);
    }

    @Override
    public String extract(MultipartFile file) throws IOException {
        return new String(file.getBytes(), StandardCharsets.UTF_8);
    }
}
