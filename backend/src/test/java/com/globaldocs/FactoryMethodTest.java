package com.globaldocs;

import com.globaldocs.factory.ColombiaProcessorFactory;
import com.globaldocs.factory.DocumentProcessorFactory;
import com.globaldocs.factory.MexicoProcessorFactory;
import com.globaldocs.model.Country;
import com.globaldocs.model.DocumentFormat;
import com.globaldocs.model.DocumentRequest;
import com.globaldocs.model.DocumentType;
import com.globaldocs.model.ProcessingResult;
import com.globaldocs.model.ProcessingStatus;
import com.globaldocs.processor.DocumentProcessor;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

class FactoryMethodTest {

    @Test
    void colombiaFactoryCreatesColombiaProcessor() {
        DocumentProcessorFactory factory = new ColombiaProcessorFactory();
        DocumentProcessor processor = factory.createProcessor();
        assertEquals(Country.COLOMBIA, processor.getCountry());
    }

    @Test
    void validFacturaElectronicaColombiaSucceeds() {
        DocumentProcessorFactory factory = new ColombiaProcessorFactory();
        DocumentProcessor processor = factory.createProcessor();

        DocumentRequest request = new DocumentRequest();
        request.setFileName("factura-001.pdf");
        request.setCountry(Country.COLOMBIA);
        request.setDocumentType(DocumentType.FACTURA_ELECTRONICA);
        request.setFormat(DocumentFormat.PDF);
        request.setFields(Map.of(
                "nit", "900123456-7",
                "cufe", "a".repeat(96)
        ));

        ProcessingResult result = processor.process(request);
        assertEquals(ProcessingStatus.SUCCESS, result.getStatus());
    }

    @Test
    void missingRfcFailsValidationInMexico() {
        DocumentProcessorFactory factory = new MexicoProcessorFactory();
        DocumentProcessor processor = factory.createProcessor();

        DocumentRequest request = new DocumentRequest();
        request.setFileName("factura.pdf");
        request.setCountry(Country.MEXICO);
        request.setDocumentType(DocumentType.FACTURA_ELECTRONICA);
        request.setFormat(DocumentFormat.PDF);

        ProcessingResult result = processor.process(request);
        assertEquals(ProcessingStatus.VALIDATION_ERROR, result.getStatus());
    }

    @Test
    void unsupportedFormatFailsValidation() {
        DocumentProcessorFactory factory = new ColombiaProcessorFactory();
        DocumentProcessor processor = factory.createProcessor();

        DocumentRequest request = new DocumentRequest();
        request.setFileName("factura.docx");
        request.setCountry(Country.COLOMBIA);
        request.setDocumentType(DocumentType.FACTURA_ELECTRONICA);
        request.setFormat(DocumentFormat.DOC);

        ProcessingResult result = processor.process(request);
        assertEquals(ProcessingStatus.VALIDATION_ERROR, result.getStatus());
        assertNotEquals(ProcessingStatus.SUCCESS, result.getStatus());
    }
}
