package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.processor.ArgentinaDocumentProcessor;
import com.globaldocs.processor.DocumentProcessor;
import org.springframework.stereotype.Component;

@Component
public class ArgentinaProcessorFactory extends DocumentProcessorFactory {

    @Override
    public DocumentProcessor createProcessor() {
        return new ArgentinaDocumentProcessor();
    }

    @Override
    public Country getCountry() {
        return Country.ARGENTINA;
    }
}
