package com.example.library.domain.exception;

public record ErrorResponseDto(
        String code,
        String message
){}