package com.example.library.dto;

public record BookResponseDto(
        long id,
        String name,
        String author
) {
    public Book toBook(){
        Book book = new Book();
        book.setId(this.id());
        book.setName(this.name());
        book.setAuthor(this.author());
        return book;
    }
}