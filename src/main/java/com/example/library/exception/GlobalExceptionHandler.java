package com.example.library.exception;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ValidationException.class)
    public ResponseEntity<ErrorResponseDto> handleBookNotFound(ValidationException ex){
        ErrorResponseDto err = new ErrorResponseDto(ex.getCode() , ex.getMessage());
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(err);
    }
}