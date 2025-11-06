package com.solidarity.api.enums;

public enum Visibility {
    PUBLIC("public"),
    PRIVATE("private");

    String label;

    Visibility(String label) {
        this.label = label;
    }
}
