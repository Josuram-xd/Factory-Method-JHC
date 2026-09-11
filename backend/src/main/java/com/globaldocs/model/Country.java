package com.globaldocs.model;

public enum Country {
    COLOMBIA("Colombia"),
    MEXICO("México"),
    ARGENTINA("Argentina"),
    CHILE("Chile");

    private final String displayName;

    Country(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return displayName;
    }
}
