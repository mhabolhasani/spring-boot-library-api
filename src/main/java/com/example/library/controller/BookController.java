package com.example.library.controller;

import com.example.library.controller.dto.*;
import com.example.library.service.domain.Book;
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
        Book newBook = bookService.add(addBookRequestDto.toBook());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.getId());
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        List<Book> allBooks = bookService.getAll();
        List<BookResponseDto> allBooksDto = allBooks.stream()
                .map(BookResponseDto::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(allBooksDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> get(@PathVariable long id) {
        Book book = bookService.get(id);
        BookResponseDto bookResponseDto = BookResponseDto.from(book);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable long id, @RequestBody UpdateBookRequestDto updateBookRequestDto){
        Book book = updateBookRequestDto.toBook();
        bookService.update(id,book);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> patch(@PathVariable long id, @RequestBody PatchBookRequestDto patchBookRequestDto) {
        Book book = patchBookRequestDto.toBook();
        bookService.patch(id, book);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}