package com.example.library.service;

import com.example.library.dto.Book;
import com.example.library.exception.ValidationException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
@Slf4j
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);

    private static final String NOT_FOUND_ERROR_CODE = "book_id_invalid";
    private static final String NOT_FOUND_ERROR_MESSAGE = "book not found";

    public Book add(Book book) {
        log.info("add new book");

        long id = ID_SEQUENCE.getAndIncrement();
        book.setId(id);
        books.put(id, book);

        log.info("book added successfully . id: {}", id);

        return book;
    }

    public List<Book> getAll() {
        log.info("getting all books");

        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .toList();
    }

    public Book get(long id) {
        log.info("getting book with id: {}", id);

        if(!books.containsKey(id)){
            log.warn("book not found with id: {}", id);
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        return books.get(id);
    }

    public Book update(long id, Book book) {
        log.info("updating book with id: {}", id);

        if (!books.containsKey(id)) {
            log.warn("book not found with id: {}", id);
            throw new ValidationException(NOT_FOUND_ERROR_CODE , NOT_FOUND_ERROR_MESSAGE);
        }
        book.setId(id);
        books.put(id, book);

        log.info("Book updated successfully with id: {}", id);

        return book;
    }

    public Book patch(long id, Book book) {
        log.info("patch book with id: {}", id);

        if(!books.containsKey(id)){
            log.warn("book not found with id: {}", id);
            throw new ValidationException(NOT_FOUND_ERROR_CODE , NOT_FOUND_ERROR_MESSAGE);
        }
        Book savedBook = books.get(id);
        if (book.getAuthor() != null) {
            savedBook.setAuthor(book.getAuthor());
        }
        if (book.getName() != null) {
            savedBook.setName(book.getName());
        }

        log.info("book patched successfully with id: {}", id);

        return savedBook;
    }

    public void delete(long id) {
        log.info("delete book with id: {}", id);

        if(!books.containsKey(id)){
            log.warn("book not found with id: {}", id);
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        books.remove(id);

        log.info("book deleted successfully with id: {}", id);
    }
}