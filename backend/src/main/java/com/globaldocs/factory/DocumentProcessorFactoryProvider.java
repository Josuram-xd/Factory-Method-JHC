package com.globaldocs.factory;

import com.globaldocs.exception.UnsupportedCountryException;
import com.globaldocs.model.Country;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

/**
 * Registro de fábricas concretas por país. Spring inyecta automáticamente
 * todos los beans que extienden {@link DocumentProcessorFactory} (uno por
 * cada país soportado) y este componente los indexa por {@link Country}.
 */
@Component
public class DocumentProcessorFactoryProvider {

    private final Map<Country, DocumentProcessorFactory> factories = new EnumMap<>(Country.class);

    public DocumentProcessorFactoryProvider(List<DocumentProcessorFactory> registeredFactories) {
        for (DocumentProcessorFactory factory : registeredFactories) {
            factories.put(factory.getCountry(), factory);
        }
    }

    public DocumentProcessorFactory getFactory(Country country) {
        DocumentProcessorFactory factory = factories.get(country);
        if (factory == null) {
            throw new UnsupportedCountryException("No hay una fábrica registrada para el país: " + country);
        }
        return factory;
    }
}
