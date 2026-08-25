package com.example.library.controller.dto;

import com.example.library.domain.Book;

public record BookResponseDto(
        String title,
        Integer publushedYear,
        Long authorId
) {
    public static BookResponseDto from(Book book) {
        return new BookResponseDto(
                book.getTitle(),
                book.getPublishedYear(),
                book.getAuthorId()
        );
    }
}