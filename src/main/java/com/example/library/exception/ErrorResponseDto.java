package com.example.library.exception;

public record ErrorResponseDto(
        String code,
        String message
){}