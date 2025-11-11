package com.solidarity.api.dto.response;

import java.util.Date;

public record SocialActionCardResponse(
        String image,
        String title,
        String city,
        String neighborhood,
        String state,
        Date startDate
) {}
