package com.globaldocs.extraction;

import com.globaldocs.model.DocumentFormat;
import org.apache.pdfbox.Loader;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

/** Extrae texto real de archivos .pdf usando Apache PDFBox. */
@Component
public class PdfContentExtractor implements ContentExtractor {

    @Override
    public Set<DocumentFormat> supportedFormats() {
        return EnumSet.of(DocumentFormat.PDF);
    }

    @Override
    public String extract(MultipartFile file) throws IOException {
        try (PDDocument document = Loader.loadPDF(file.getBytes())) {
            return new PDFTextStripper().getText(document);
        }
    }
}
