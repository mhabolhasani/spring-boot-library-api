package com.example.library.application.port.out;

import com.example.library.domain.Book;

import java.util.List;
import java.util.Optional;

public interface BookPersistencePort {

    Book save(Book book);

    List<Book> findAll();

    Optional<Book> findById(Long id);

    List<Book> search(
            String title,
            String author,
            String isbn,
            Integer maxPageCount
    );

    void deleteById(Long id);
}