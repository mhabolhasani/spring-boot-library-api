package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBookRequestDto(
        @NotBlank
        String name,

        @NotBlank
        String author
) {
    public BookResponseDto toBookResponse(long id) {
        return new BookResponseDto(id, this.name, this.author);
    }
}