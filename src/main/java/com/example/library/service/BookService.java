package com.example.library.service;

import com.example.library.dto.*;
import com.example.library.exception.ValidationException;
import org.springframework.stereotype.Service;

import java.security.PublicKey;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    private static final AtomicLong ID_SEQUENCE = new AtomicLong(1);

    private static final String NOT_FOUND_ERROR_CODE = "book_id_invalid";
    private static final String NOT_FOUND_ERROR_MESSAGE = "book not found";

    public Book add(Book book) {
        long id = ID_SEQUENCE.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public List<BookResponse> getAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .map(Book::toBookResponse)
                .toList();
    }

    public BookResponse get(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        BookResponse bookResponse = books.get(id).toBookResponse();
        return bookResponse;
    }

    public long update(long id, UpdateBookRequestDto updateBookRequestDto) {
        if (!books.containsKey(id)) {
            throw new ValidationException(NOT_FOUND_ERROR_CODE, NOT_FOUND_ERROR_MESSAGE);
        }
        Book book = new Book();
        book.setAuthor(updateBookRequestDto.author());
        book.setName(updateBookRequestDto.name());
        book.setId(id);
        books.put(id, book);
        return id;
    }

    public long patch(long id, PatchBookRequestDto patchBookRequestDto) {
        if(!books.containsKey(id)){
            throw new ValidationException(NOT_FOUND_ERROR_CODE , NOT_FOUND_ERROR_MESSAGE);
        }
        Book savedBook = books.get(id);
        if (patchBookRequestDto.author() != null) {
            savedBook.setAuthor(patchBookRequestDto.author());
        }
        if (patchBookRequestDto.name() != null) {
            savedBook.setName(patchBookRequestDto.name());
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