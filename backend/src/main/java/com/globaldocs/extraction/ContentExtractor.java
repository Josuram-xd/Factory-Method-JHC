package com.globaldocs.extraction;

import com.globaldocs.model.DocumentFormat;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Set;

/**
 * Estrategia de extracción de texto por formato de archivo. Cada
 * implementación sabe leer un formato (o grupo de formatos) concreto.
 */
public interface ContentExtractor {

    Set<DocumentFormat> supportedFormats();

    String extract(MultipartFile file) throws IOException;
}
