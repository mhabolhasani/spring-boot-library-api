package com.example.library.controller.dto;

import jakarta.validation.constraints.NotBlank;

public record UpdateBookRequestDto(
        @NotBlank
        String name,

        @NotBlank
        String author
) {}