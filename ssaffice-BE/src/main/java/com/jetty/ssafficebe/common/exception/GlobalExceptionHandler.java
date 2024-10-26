package com.jetty.ssafficebe.common.exception;

import com.jetty.ssafficebe.common.exception.exceptiontype.DuplicateValueException;
import com.jetty.ssafficebe.common.payload.ApiResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(DuplicateValueException.class)
    protected ResponseEntity<ApiResponse> handleDuplicateValueException(DuplicateValueException e) {

        return ResponseEntity.status(e.getErrorCode().getStatus())
                             .body(new ApiResponse(false, e.getMessage(), e.getData()));
    }

}
