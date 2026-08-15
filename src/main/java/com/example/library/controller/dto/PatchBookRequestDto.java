package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;

public record PatchBookRequestDto(
        String name,
        String author
) {
    public BookResponseDto toBookResponse(long id) {
        return new BookResponseDto(id, this.name, this.author);
    }
}