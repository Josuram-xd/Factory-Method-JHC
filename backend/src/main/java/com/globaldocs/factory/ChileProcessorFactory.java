package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.processor.ChileDocumentProcessor;
import com.globaldocs.processor.DocumentProcessor;
import org.springframework.stereotype.Component;

@Component
public class ChileProcessorFactory extends DocumentProcessorFactory {

    @Override
    public DocumentProcessor createProcessor() {
        return new ChileDocumentProcessor();
    }

    @Override
    public Country getCountry() {
        return Country.CHILE;
    }
}
