package com.example.library.application.service;

import com.example.library.application.port.in.BookUseCase;
import com.example.library.application.port.out.BookPersistencePort;
import com.example.library.domain.exception.ValidationException;
import com.example.library.domain.Book;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService implements BookUseCase {

    private static final String BOOK_NOT_FOUND_CODE = "book_id_invalid";
    private static final String BOOK_NOT_FOUND_MESSAGE = "book not found";

    private final BookPersistencePort bookPersistencePort;

    public Book add(Book book) {
        return bookPersistencePort.save(book);
    }

    public List<Book> getAll() {
        return bookPersistencePort.findAll();
    }

    public Book get(Long id) {
        return bookPersistencePort.findById(id)
                .orElseThrow(() -> new ValidationException(BOOK_NOT_FOUND_CODE, BOOK_NOT_FOUND_MESSAGE));
    }

    public List<Book> search(String title ,
                                String author_name ,
                                String isbn ,
                                String page_count){
        Integer maxPageCount = (page_count != null && !page_count.isBlank())
                ? Integer.parseInt(page_count)
                : null;
        return bookPersistencePort.search(title ,
                author_name ,
                isbn,
                maxPageCount);
    }

    public Book update(Long id, Book book) {
        get(id);
        book.setId(id);
        return bookPersistencePort.save(book);
    }

    public Book patch(Long id, Book book) {
        Book existing = get(id);
        Book merged = Book.builder()
                .id(id)
                .title(book.getTitle() != null ? book.getTitle() : existing.getTitle())
                .isbn(book.getIsbn() != null ? book.getIsbn() : existing.getIsbn())
                .publishedYear(book.getPublishedYear() != null ? book.getPublishedYear() : existing.getPublishedYear())
                .authorId(book.getAuthorId() != null
                        ? book.getAuthorId()
                        :existing.getAuthorId())
                .categories(book.getCategories() != null
                        ? (book.getCategories())
                        : existing.getCategories())
                .build();

        return bookPersistencePort.save(merged);
    }

    public void delete(Long id) {
        bookPersistencePort.deleteById(id);
    }
}