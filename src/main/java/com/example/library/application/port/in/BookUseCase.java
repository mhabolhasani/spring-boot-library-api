package com.example.library.application.port.in;

import com.example.library.domain.Book;

import java.util.List;

public interface BookUseCase {

    Book add(Book book);

    List<Book> getAll();

    Book get(Long id);

    List<Book> search(String title,
                       String author_name,
                       String isbn,
                       String page_count);

    Book update(Long id, Book book);

    Book patch(Long id, Book book);

    void delete(Long id);
}