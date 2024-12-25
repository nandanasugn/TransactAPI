package com.nandana.transactapi.util;

import com.nandana.transactapi.dto.response.CommonResponse;
import com.nandana.transactapi.exception.CustomException;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(CustomException.class)
    public ResponseEntity<CommonResponse<?>> handleCustomException(CustomException ex) {
        CommonResponse<?> response = DtoMapper.mapToCommonResponse(ex.getStatus(), ex.getMessage(), null);
        return new ResponseEntity<>(response, HttpStatusCode.valueOf(ex.getStatus()));
    }

    @ExceptionHandler(ConstraintViolationException.class)
    public ResponseEntity<CommonResponse<?>> handleConstraintViolationException(ConstraintViolationException ex) {
        String violationMessage = ex.getConstraintViolations().stream().map(ConstraintViolation::getMessage).findFirst().orElse("Validation failed");

        CommonResponse<?> response = DtoMapper.mapToCommonResponse(HttpStatus.BAD_REQUEST.value(), violationMessage, null);

        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<CommonResponse<?>> handleGeneralException(Exception ex) {
        CommonResponse<?> response = new CommonResponse<>(HttpStatus.INTERNAL_SERVER_ERROR.value(), "An unexpected error occurred", null);
        return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR);
    }
}
