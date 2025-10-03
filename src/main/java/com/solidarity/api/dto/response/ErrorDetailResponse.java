package com.solidarity.api.dto.response;

import lombok.Getter;

@Getter
public class ErrorDetailResponse {
    private String field;
    private String message;

    public ErrorDetailResponse(String field, String message) {
        this.field = field;
        this.message = message;
    }
}
