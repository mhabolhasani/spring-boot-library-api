package com.example.library.adapter.in.web;

import com.example.library.adapter.in.web.dto.*;
import com.example.library.application.port.in.BookUseCase;
import com.example.library.domain.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
public class BookController {

    private final BookUseCase bookUseCase;

    public BookController(BookUseCase bookUseCase) {
        this.bookUseCase = bookUseCase;
    }

    @PostMapping
    public ResponseEntity<Long> add(@RequestBody AddBookRequestDto addBookRequestDto) {
        Book newBook = bookUseCase.add(addBookRequestDto.toBook());
        return ResponseEntity.status(HttpStatus.CREATED).body(newBook.getId());
    }

    @GetMapping
    public ResponseEntity<List<BookResponseDto>> getAll() {
        List<Book> allBooks = bookUseCase.getAll();
        List<BookResponseDto> allBooksDto = allBooks.stream()
                .map(BookResponseDto::from)
                .toList();
        return ResponseEntity.status(HttpStatus.OK).body(allBooksDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<BookResponseDto> get(@PathVariable long id) {
        Book book = bookUseCase.get(id);
        BookResponseDto bookResponseDto = BookResponseDto.from(book);
        return ResponseEntity.status(HttpStatus.OK).body(bookResponseDto);
    }

    @GetMapping("/available")
    public ResponseEntity<List<BookResponseDto>> search(
            @RequestParam(required = false) String title ,
            @RequestParam(required = false) String author_name ,
            @RequestParam(required = false) String isbn ,
            @RequestParam(required = false) String page_count){
        return ResponseEntity.status(HttpStatus.OK).body(bookUseCase.search(title,author_name,isbn,page_count).stream()
                .map(BookResponseDto::from)
                .toList()
        );
    }


    @PutMapping("/{id}")
    public ResponseEntity<Long> update(@PathVariable long id, @RequestBody UpdateBookRequestDto updateBookRequestDto){
        Book book = updateBookRequestDto.toBook();
        bookUseCase.update(id,book);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<Long> patch(@PathVariable long id, @RequestBody PatchBookRequestDto patchBookRequestDto) {
        Book book = patchBookRequestDto.toBook();
        bookUseCase.patch(id, book);
        return ResponseEntity.ok(id);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Long> delete(@PathVariable long id) {
        bookUseCase.delete(id);
        return ResponseEntity.status(HttpStatus.OK).body(id);
    }
}