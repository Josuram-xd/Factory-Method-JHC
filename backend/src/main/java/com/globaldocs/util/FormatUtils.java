package com.globaldocs.util;

import com.globaldocs.model.DocumentFormat;

import java.util.Optional;

public final class FormatUtils {

    private FormatUtils() {
    }

    public static Optional<DocumentFormat> fromFilename(String filename) {
        if (filename == null) {
            return Optional.empty();
        }
        int dot = filename.lastIndexOf('.');
        if (dot < 0 || dot == filename.length() - 1) {
            return Optional.empty();
        }
        String extension = filename.substring(dot + 1).toUpperCase();
        try {
            return Optional.of(DocumentFormat.valueOf(extension));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }
}
