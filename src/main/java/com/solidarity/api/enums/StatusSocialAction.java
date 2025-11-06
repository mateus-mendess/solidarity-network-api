package com.solidarity.api.enums;

public enum StatusSocialAction {
    ACTIVE("active"),
    FINISHED("finished"),
    CANCELLED("cancelled");

    String label;

    StatusSocialAction(String label) {
        this.label = label;
    }
}
