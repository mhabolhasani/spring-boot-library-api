package com.example.library.controller.mapper;

import com.example.library.controller.dto.*;
import com.example.library.dto.Book;

import java.util.List;

public final class BookMapper {

    private BookMapper() {
    }

    public static Book toBook(AddBookRequestDto dto) {
        return new Book(
                0,
                dto.name(),
                dto.author()
        );
    }

    public static Book toBook(UpdateBookRequestDto dto) {
        return new Book(
                0,
                dto.name(),
                dto.author()
        );
    }

    public static Book toBook(PatchBookRequestDto dto) {
        return new Book(
                0,
                dto.name(),
                dto.author()
        );
    }

    public static BookResponseDto toResponseDto(Book book) {
        return BookResponseDto.from(book);
    }

    public static List<BookResponseDto> toResponseDtos(List<Book> books) {
        return books.stream()
                .map(BookResponseDto::from)
                .toList();
    }
}