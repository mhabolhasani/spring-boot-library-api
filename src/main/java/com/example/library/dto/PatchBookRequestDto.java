package com.example.library.dto;

import jakarta.validation.constraints.NotBlank;

public record PatchBookRequestDto(
        String name,
        String author
) {
    public BookResponse toBookResponse(long id) {
        return new BookResponse(id, this.name, this.author);
    }
}