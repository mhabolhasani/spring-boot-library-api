package com.example.library.controller.dto;

import com.example.library.dto.Book;
import jakarta.validation.constraints.NotBlank;

public record AddBookRequestDto(
        @NotBlank
        String name,

        @NotBlank
        String author
){
        public Book toBook() {
                return new Book(
                        null,
                        name,
                        author
                );
        }
}