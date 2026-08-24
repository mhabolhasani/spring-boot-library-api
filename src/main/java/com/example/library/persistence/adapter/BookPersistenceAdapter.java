package com.example.library.persistence.adapter;

import com.example.library.domain.Book;
import com.example.library.domain.Category;
import com.example.library.domain.mapper.BookMapper;
import com.example.library.exception.ValidationException;
import com.example.library.persistence.entity.*;
import com.example.library.persistence.repository.*;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class BookPersistenceAdapter {

    private static final String BOOK_NOT_FOUND_CODE = "book_id_invalid";
    private static final String BOOK_NOT_FOUND_MESSAGE = "book not found";
    private static final String AUTHOR_NOT_FOUND_CODE = "author_id_invalid";
    private static final String AUTHOR_NOT_FOUND_MESSAGE = "author not found";
    private static final String CATEGORY_NOT_FOUND_CODE = "category_id_invalid";
    private static final String CATEGORY_NOT_FOUND_MESSAGE = "one or more categories not found";

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;
    private final CategoryRepository categoryRepository;

    public BookPersistenceAdapter(BookRepository bookRepository,
                                  AuthorRepository authorRepository,
                                  CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

    public List<Book> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDomain)
                .toList();
    }

    public Optional<Book> findById(Integer id) {
        return bookRepository.findById(id).map(BookMapper::toDomain);
    }

    public Book save(Book book) {
        BookEntity entity = BookEntity.builder().build();

        entity.setTitle(book.getTitle());
        entity.setIsbn(book.getIsbn());
        entity.setPublishedYear(book.getPublishedYear());
        entity.setAuthor(findAuthor(book.getAuthor().getId()));
        entity.setCategories(findCategories(book.getCategories()));

        return BookMapper.toDomain(bookRepository.save(entity));
    }

    public void deleteById(Integer id) {
        bookRepository.delete(getEntityOrThrow(id));
    }

    private BookEntity getEntityOrThrow(Integer id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ValidationException(BOOK_NOT_FOUND_CODE, BOOK_NOT_FOUND_MESSAGE));
    }

    private AuthorEntity findAuthor(Integer authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ValidationException(AUTHOR_NOT_FOUND_CODE, AUTHOR_NOT_FOUND_MESSAGE));
    }

    private List<CategoryEntity> findCategories(List<Category> categories) {
        if (categories == null || categories.isEmpty()) {
            return new ArrayList<>();
        }
        List<Integer> ids = categories.stream().map(Category::getId).toList();
        List<CategoryEntity> entities = categoryRepository.findAllById(ids);
        if (entities.size() != ids.size()) {
            throw new ValidationException(CATEGORY_NOT_FOUND_CODE, CATEGORY_NOT_FOUND_MESSAGE);
        }
        return entities;
    }
}
