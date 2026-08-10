package com.example.library.service;

import com.example.library.dto.Book;
import com.example.library.exception.BookNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Service
public class BookService {

    private final Map<Long, Book> books = new ConcurrentHashMap<>();

    private static final AtomicLong num = new AtomicLong(1);

    public List<Book> getAllBooks() {
        return books.values().stream()
                .sorted(Comparator.comparingLong(Book::getId))
                .toList();
    }

    public Book getBook(long id) {
        if(!books.containsKey(id)){
            throw new BookNotFoundException("1" , "not found");
        }
        return books.get(id);
    }

    public void deleteBook(long id) {
        if(!books.containsKey(id)){
            throw new BookNotFoundException("2" , "not found");
        }
        books.remove(id);
    }

    public Book addBook(Book book) {
        long id = num.getAndIncrement();
        book.setId(id);
        books.put(id, book);
        return book;
    }

    public Book updateBook(long id, Book book) {
        if(!books.containsKey(id)){
            throw new BookNotFoundException("3" , "not found");
        }
        Book bookInMemory = books.get(id);
        if (book.getAuthor() != null) {
            bookInMemory.setAuthor(book.getAuthor());
        }
        if (book.getName() != null) {
            bookInMemory.setName(book.getName());
        }
        return bookInMemory;
    }

    public Book putBook(long id, Book book) {
        if (!books.containsKey(id)) {
            throw new BookNotFoundException("4" , "not found");
        }
        book.setId(id);
        books.put(id, book);
        return book;
    }
}