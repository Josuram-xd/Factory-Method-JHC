package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.processor.ColombiaDocumentProcessor;
import com.globaldocs.processor.DocumentProcessor;
import org.springframework.stereotype.Component;

@Component
public class ColombiaProcessorFactory extends DocumentProcessorFactory {

    @Override
    public DocumentProcessor createProcessor() {
        return new ColombiaDocumentProcessor();
    }

    @Override
    public Country getCountry() {
        return Country.COLOMBIA;
    }
}
