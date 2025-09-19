package com.solidarity.api.enums;

import lombok.Getter;

@Getter
public enum RolesStatus {
    ROLE_VOLUNTEER("Volunteer"),
    ROLE_ORGANIZATION("Organization"),
    ROLE_MODERATOR("Moderator");

    String label;

    RolesStatus(String label) {
        this.label = label;
    }

}
