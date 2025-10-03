package com.solidarity.api.dto.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Getter;

import java.time.LocalDateTime;
import java.util.List;

@Getter
public class ErrorResponse {
    private LocalDateTime timestamp;
    private Integer status;
    private String error;
    private String message;
    private String path;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private List<ErrorDetailResponse> errorsDetails;

    public ErrorResponse(Integer status, String error, String message, String path) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
    }

    public ErrorResponse(Integer status, String error, String message, String path, List<ErrorDetailResponse> errorsDetails) {
        this.timestamp = LocalDateTime.now();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errorsDetails = errorsDetails;
    }
}
