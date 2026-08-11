package com.example.library.controller;

import com.example.library.dto.*;
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
    public ResponseEntity<Long> add(@RequestBody AddBookRequestDto addBookRequestDto) {
        BookResponse newBook = bookService.add(addBookRequestDto);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.id());
    }

    @GetMapping
    public ResponseEntity<List<BookResponse>> getAll() {
        List<BookResponse> allBooks = bookService.getAll();
        return ResponseEntity.status(HttpStatus.OK).body(allBooks);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponse> get(@PathVariable long id) {
        BookResponse bookResponse = bookService.get(id);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable long id, @RequestBody UpdateBookRequestDto updateBookRequestDto){
        bookService.update(id,updateBookRequestDto);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> patch(@PathVariable long id, @RequestBody PatchBookRequestDto patchBookRequestDto) {
        bookService.patch(id, patchBookRequestDto);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}