package com.solidarity.api.dto.response;

import java.util.UUID;

public record OrganizationResponse(
    UUID id,
    String cnpj,
    String email,
    String phone,
    String websiteUrl

) {}
