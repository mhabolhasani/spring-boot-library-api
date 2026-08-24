package com.example.library.service;

import com.example.library.exception.ValidationException;
import com.example.library.persistence.entity.BookEntity;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class BookService {
    private static final String NOT_FOUND_ERROR_CODE = "book_id_invalid";
    private static final String NOT_FOUND_ERROR_MESSAGE = "book not found";

    public BookEntity add(Book book) {
        long id = ID_SEQUENCE.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public List<Book> getAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .toList();
    }

    public Book get(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        return books.get(id);
    }

    public long update(long id, Book book) {
        if (!books.containsKey(id)) {
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        Book updatedBook = new Book(
                id,
                book.getName(),
                book.getAuthor()
        );
        books.put(id, updatedBook);
        return id;
    }

    public long patch(long id, Book book) {
        if(!books.containsKey(id)){
            throw new ValidationException(NOT_FOUND_ERROR_CODE , NOT_FOUND_ERROR_MESSAGE);
        }
        Book savedBook = books.get(id);
        if (book.getAuthor() != null) {
            savedBook.setAuthor(book.getAuthor());
        }
        if (book.getName() != null) {
            savedBook.setName(book.getName());
        }
        return id;
    }

    public void delete(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        books.remove(id);
    }
}