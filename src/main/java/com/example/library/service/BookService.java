package com.example.library.service;

import com.example.library.exception.ValidationException;
import com.example.library.persistence.adapter.BookPersistenceAdapter;
import com.example.library.service.domain.Book;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BookService {

    private static final String BOOK_NOT_FOUND_CODE = "book_id_invalid";
    private static final String BOOK_NOT_FOUND_MESSAGE = "book not found";

    private final BookPersistenceAdapter bookPersistenceAdapter;

    public BookService(BookPersistenceAdapter bookPersistenceAdapter) {
        this.bookPersistenceAdapter = bookPersistenceAdapter;
    }

    public Book add(Book book) {
        return bookPersistenceAdapter.save(book);
    }

    public List<Book> getAll() {
        return bookPersistenceAdapter.findAll();
    }

    public Book get(Long id) {
        return bookPersistenceAdapter.findById(id)
                .orElseThrow(() -> new ValidationException(BOOK_NOT_FOUND_CODE, BOOK_NOT_FOUND_MESSAGE));
    }

    public List<Book> search(String title ,
                                String authorName ,
                                String isbn ,
                                String pageCount){
        Integer maxPageCount = (pageCount != null && !pageCount.isBlank())
                ? Integer.parseInt(pageCount)
                : null;
        return bookPersistenceAdapter.search(title ,
                authorName ,
                isbn,
                maxPageCount);
    }

    public Book update(Long id, Book book) {
        get(id);
        book.setId(id);
        return bookPersistenceAdapter.save(book);
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

        return bookPersistenceAdapter.save(merged);
    }

    public void delete(Long id) {
        bookPersistenceAdapter.deleteById(id);
    }

    public List<Book> search(
            String title,
            String author,
            String isbn,
            Integer maxPageCount
    ) {
        return bookPersistenceAdapter.search(
                title,
                author,
                isbn,
                maxPageCount
        );
    }
}