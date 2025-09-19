package com.solidarity.api.enums;

import lombok.Getter;

@Getter
public enum Gender {
    MALE("male"),
    FEMALE("female"),
    OTHER("other");

    String label;

    Gender(String label) {
        this.label = label;
    }

    public static Gender fromLabel(String label) {
        for (Gender g : values()) {
            if (g.label.equalsIgnoreCase(label)) {
                return g;
            }
        }
        throw new IllegalArgumentException("Gender invalid");
    }
}
