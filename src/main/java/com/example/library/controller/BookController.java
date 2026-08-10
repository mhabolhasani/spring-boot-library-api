package com.example.library.controller;

import com.example.library.dto.Book;
import com.example.library.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookService bookService;

    public BookController(BookService bookService) {
        this.bookService = bookService;
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody Book book) {
        Book newBook = bookService.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.getId());
    }

    @GetMapping
    public ResponseEntity<List<Book>> getAll() {
        List<Book> allBooks = bookService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Book> get(@PathVariable long id) {
        Book book = bookService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(book);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable long id, @RequestBody Book book){
        Book updatedBook = bookService.update(id,book);
        if (updatedBook==null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.status(HttpStatus.OK).body(updatedBook.getId());
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> patch(@PathVariable long id, @RequestBody Book book) {
        Book updatedBook = bookService.patch(id, book);
        if (updatedBook == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(updatedBook.getId());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}