package com.solidarity.api.handler;

import com.fasterxml.jackson.databind.deser.impl.BeanAsArrayDeserializer;
import com.solidarity.api.dto.response.ErrorDetailResponse;
import com.solidarity.api.dto.response.ErrorResponse;
import com.solidarity.api.exception.BusinessException;
import com.solidarity.api.exception.FileStorageException;
import com.solidarity.api.exception.NotFoundException;
import com.solidarity.api.exception.SolidarityException;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.List;

@ControllerAdvice
public class GlobalHandlerException {

    @ExceptionHandler(NotFoundException.class)
    public ResponseEntity<ErrorResponse> handleNotFoundException(NotFoundException exception, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not Found",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(error);
    }

    @ExceptionHandler(FileStorageException.class)
    public ResponseEntity<ErrorResponse> handleFileStorageException(FileStorageException exception, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "File storage failure",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }

    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<ErrorResponse> handleBusinessException(BusinessException exception, HttpServletRequest request) {
        ErrorResponse error = new ErrorResponse(
                HttpStatus.UNPROCESSABLE_ENTITY.value(),
                "Broken business rule",
                exception.getMessage(),
                request.getRequestURI()
        );

        return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY).body(error);
    }
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorResponse> handleValidationException(MethodArgumentNotValidException exception, HttpServletRequest request) {
        List<String> globalErrors = exception.getBindingResult().getGlobalErrors().stream().map(ObjectError::getDefaultMessage).toList();

        ErrorResponse error = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Validation Error",
                "Invalid request fields",
                request.getRequestURI(),
                exception
                        .getBindingResult()
                        .getFieldErrors()
                        .stream()
                        .map(errorValidation -> new ErrorDetailResponse(errorValidation.getField(), errorValidation.getDefaultMessage()))
                        .toList(),
                globalErrors

        );

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(error);
    }
}
