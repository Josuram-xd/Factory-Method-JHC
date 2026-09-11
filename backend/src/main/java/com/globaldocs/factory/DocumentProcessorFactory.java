package com.globaldocs.factory;

import com.globaldocs.model.Country;
import com.globaldocs.processor.DocumentProcessor;

/**
 * "Creator" del patrón Factory Method. Cada país concreto decide qué
 * subclase de {@link DocumentProcessor} instanciar mediante createProcessor().
 */
public abstract class DocumentProcessorFactory {

    /** Factory Method: las subclases deciden qué DocumentProcessor concreto crear. */
    public abstract DocumentProcessor createProcessor();

    public abstract Country getCountry();
}
