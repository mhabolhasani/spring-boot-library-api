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

    private static final String ERROR_CODE = "book_id_invalid";
    private static final String ERROR_MESSAGE = "book not found";

    public List<BookResponse> getAll() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .map(Book::toBookResponse)
                .toList();
    }

    public BookResponse get(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE);
        }
        BookResponse bookResponse = books.get(id).toBookResponse();
        return bookResponse;
    }

    public void delete(long id) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE, ERROR_MESSAGE);
        }
        books.remove(id);
    }

    public BookResponse add(AddBookRequestDto addBookRequestDto) {
        long id = ID_SEQUENCE.getAndIncrement();
        BookResponse bookResponse = addBookRequestDto.toBookResponse(id);
        Book book = bookResponse.toBook();
        books.put(id, book);
        return bookResponse;
    }

    public long patch(long id, PatchBookRequestDto patchBookRequestDto) {
        if(!books.containsKey(id)){
            throw new ValidationException(ERROR_CODE , ERROR_MESSAGE);
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

    public long update(long id, UpdateBookRequestDto updateBookRequestDto) {
        if (!books.containsKey(id)) {
            throw new ValidationException(ERROR_CODE , ERROR_MESSAGE);
        }
        Book book = new Book();
        book.setAuthor(updateBookRequestDto.author());
        book.setName(updateBookRequestDto.name());
        book.setId(id);
        books.put(id, book);
        return id;
    }
}