package com.example.library.controller.dto;

import com.example.library.dto.Book;

public record BookResponseDto(
        long id,
        String name,
        String author
) {
    public static BookResponseDto from(Book book) {
        return new BookResponseDto(
                book.getId(),
                book.getName(),
                book.getAuthor()
        );
    }
}