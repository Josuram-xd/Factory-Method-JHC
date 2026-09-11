package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.processor.DocumentProcessor;
import com.globaldocs.processor.MexicoDocumentProcessor;
import org.springframework.stereotype.Component;

@Component
public class MexicoProcessorFactory extends DocumentProcessorFactory {

    @Override
    public DocumentProcessor createProcessor() {
        return new MexicoDocumentProcessor();
    }

    @Override
    public Country getCountry() {
        return Country.MEXICO;
    }
}
