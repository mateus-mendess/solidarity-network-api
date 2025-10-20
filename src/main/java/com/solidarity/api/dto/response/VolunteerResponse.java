package com.solidarity.api.dto.response;

import java.time.LocalDate;
import java.util.UUID;

public record VolunteerResponse(
        UUID id,
        String name,
        String lastName,
        String profilePhoto,
        String gender,
        String work,
        String phone,
        LocalDate birthday
) {}
