package com.example.library.controller;

import com.example.library.controller.dto.*;
import com.example.library.controller.mapper.BookMapper;
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
    public ResponseEntity<Long> add(@RequestBody AddBookRequestDto addBookRequestDto) {
        Book newBook = bookService.add(BookMapper.toBook(addBookRequestDto));
        BookResponseDto bookResponseDto = BookMapper.toResponseDto(newBook);
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.getId());
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        List<Book> allBooks = bookService.getAll();
        List<BookResponseDto> allBooksDto = BookMapper.toResponseDtos(allBooks);
        return ResponseEntity.status(HttpStatus.OK).body(allBooksDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> get(@PathVariable long id) {
        Book book = bookService.get(id);
        BookResponseDto bookResponseDto = BookMapper.toResponseDto(book);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDto);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable long id, @RequestBody UpdateBookRequestDto updateBookRequestDto){
        Book book = BookMapper.toBook(updateBookRequestDto);
        bookService.update(id,book);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> patch(@PathVariable long id, @RequestBody PatchBookRequestDto patchBookRequestDto) {
        Book book = BookMapper.toBook(patchBookRequestDto);
        bookService.patch(id, book);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookService.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}