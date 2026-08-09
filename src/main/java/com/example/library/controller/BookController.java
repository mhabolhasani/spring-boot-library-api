package com.example.library.controller;

import com.example.library.dto.Book;
import com.example.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> allBooks = bookService.getAllBooks();
        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> getBook(@PathVariable long id) {
        Book book = bookService.getBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> deleteBook(@PathVariable long id) {
        bookService.deleteBook(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PostMapping
    public ResponseEntity<Long> addBook(@RequestBody Book book) {
        Book newBook = bookService.addBook(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.getId());
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> putBook(@PathVariable long id, @RequestBody Book book){
        bookService.putBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(book.getId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> updateBook(@PathVariable long id, @RequestBody Book book){
        Book updatedBook = bookService.updateBook(book);
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook.getId());
    }
}