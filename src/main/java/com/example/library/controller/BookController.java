package com.example.library.controller;

import com.example.library.dto.Book;
import com.example.library.service.BookService;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public HashMap<Long, Book> getAllBooks() {
        return bookService.getAllBooks();
    }

    @GetMapping("/{id}")
    public Book getBook(@PathVariable long id) {
        return bookService.getBook(id);
    }

    @DeleteMapping("/{id}")
    public String deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return "Book deleted";
    }

    @PostMapping
    public Book addBook(@RequestBody Book book) {
        bookService.addBook(book);
        return book;
    }

    @PutMapping("/{id}")
    public Book putBook(@PathVariable long id, @RequestBody Book book) {
        bookService.putBook(book);
        return book;
    }

    @PatchMapping("/{id}")
    public Book updateBook(@PathVariable long id, @RequestBody Book book) {
        bookService.updateBook(book);
        return book;
    }
}
