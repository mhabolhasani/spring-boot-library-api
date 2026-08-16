package com.example.library.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record PatchBookRequestDto(
        String name,
        String author
){}