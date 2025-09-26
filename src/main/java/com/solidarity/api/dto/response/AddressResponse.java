package com.solidarity.api.dto.response;

public record AddressResponse(
        String postalCode,
        String neighborhood,
        String street,
        String city,
        String state
) {}
