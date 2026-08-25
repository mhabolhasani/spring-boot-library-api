package com.example.library.controller.dto;

import com.example.library.domain.Book;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.util.List;

public record AddBookRequestDto(
        @NotBlank
        String title,

        @NotBlank
        String isbn,

        @NotNull
        Integer publishedYear,

        @NotNull
        Long authorId,

        @NotNull
        List<Long> categoryIds
){
        public Book toBook() {
                return new Book(
                        null,
                        this.title(),
                        this.isbn(),
                        this.publishedYear(),
                        this.authorId(),
                        this.categoryIds()
                );
        }
}