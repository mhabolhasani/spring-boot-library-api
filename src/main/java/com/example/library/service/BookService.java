package com.example.library.service;

import com.example.library.dto.Book;
import com.example.library.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);

    private static final String ERROR_CODE = "book_id_invalid";
    private static final String ERROR_MESSAGE = "book not found";

    public List<Book> getAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .toList();
    }

    public Book get(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE);
        }
        return books.get(id);
    }

    public void delete(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE);
        }
        books.remove(id);
    }

    public Book add(Book book) {
        long id = ID_SEQUENCE.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public Book patch(long id, Book book) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE , ERROR_MESSAGE);
        }
        Book savedBook = books.get(id);
        if (book.getAuthor() != null) {
            savedBook.setAuthor(book.getAuthor());
        }
        if (book.getName() != null) {
            savedBook.setName(book.getName());
        }
        return savedBook;
    }

    public Book update(long id, Book book) {
        if (!books.containsKey(id)) {
            throw new ValidationException(ERROR_CODE , ERROR_MESSAGE);
        }
        book.setId(id);
        books.put(id, book);
        return book;
    }
}