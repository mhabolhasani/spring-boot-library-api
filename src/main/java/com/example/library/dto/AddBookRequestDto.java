package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;

public record AddBookRequestDto(
        @NotBlank
        String name,

        @NotBlank
        String author
) {
    public BookResponse toBookResponse(long id) {
        return new BookResponse(id, this.name, this.author);
    }
}