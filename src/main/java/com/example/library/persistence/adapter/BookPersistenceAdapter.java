package com.example.library.persistence.adapter;

import com.example.library.service.domain.Book;
import com.example.library.persistence.mapper.BookMapper;
import com.example.library.exception.ValidationException;
import com.example.library.persistence.entity.*;
import com.example.library.persistence.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
@AllArgsConstructor
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

    @PersistenceContext
    private EntityManager entityManager;

    public Book save(Book book) {
        BookEntity entity = (book.getId() != null)
                ? getEntityOrThrow(book.getId())
                : BookEntity.builder().build();

        entity.setTitle(book.getTitle());
        entity.setIsbn(book.getIsbn());
        entity.setPublishedYear(book.getPublishedYear());
        entity.setAuthor(findAuthor(book.getAuthorId()));
        entity.setCategories(findCategories(book.getCategories()));

        return BookMapper.toDomain(bookRepository.save(entity));
    }

    public List<Book> findAll() {
        return bookRepository.findAll().stream()
                .map(BookMapper::toDomain)
                .toList();
    }

    public Optional<Book> findById(Long id) {
        return bookRepository.findById(id).map(BookMapper::toDomain);
    }

    public List<Book> search(
            String title,
            String author,
            String isbn,
            Integer maxPageCount
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);

        Root<BookEntity> book = query.from(BookEntity.class);

        Predicate predicate = cb.conjunction();

        if (title != null && !title.isBlank()) {
            predicate = cb.and(
                    predicate,
                    cb.like(
                            cb.lower(book.get("title")),
                            "%" + title.toLowerCase() + "%"
                    )
            );
        }

        if (author != null && !author.isBlank()) {
            Join<BookEntity, AuthorEntity> authorJoin =
                    book.join("author");

            predicate = cb.and(
                    predicate,
                    cb.like(
                            cb.lower(authorJoin.get("name")),
                            "%" + author.toLowerCase() + "%"
                    )
            );
        }

        if (isbn != null && !isbn.isBlank()) {
            predicate = cb.and(
                    predicate,
                    cb.equal(book.get("isbn"), isbn)
            );
        }

        if (maxPageCount != null) {
            Root<BookDetailEntity> bookDetail = query.from(BookDetailEntity.class);
            predicate = cb.and(
                    predicate,
                    cb.equal(bookDetail.get("book"), book),
                    cb.lessThanOrEqualTo(
                            bookDetail.get("pageCount"),
                            maxPageCount
                    )
            );
        }
        query.where(predicate);
        return entityManager
                .createQuery(query)
                .getResultList().stream()
                .map(BookMapper::toDomain).toList();
    }

    public void deleteById(Long id) {
        bookRepository.delete(getEntityOrThrow(id));
    }

    private BookEntity getEntityOrThrow(Long id) {
        return bookRepository.findById(id)
                .orElseThrow(() -> new ValidationException(BOOK_NOT_FOUND_CODE, BOOK_NOT_FOUND_MESSAGE));
    }

    private AuthorEntity findAuthor(Long authorId) {
        return authorRepository.findById(authorId)
                .orElseThrow(() -> new ValidationException(AUTHOR_NOT_FOUND_CODE, AUTHOR_NOT_FOUND_MESSAGE));
    }

    private List<CategoryEntity> findCategories(List<Long> categoryIds) {
        if (categoryIds == null || categoryIds.isEmpty()) {
            return new ArrayList<>();
        }
        List<CategoryEntity> entities = categoryRepository.findAllById(categoryIds);
        if (entities.size() != categoryIds.size()) {
            throw new ValidationException(CATEGORY_NOT_FOUND_CODE, CATEGORY_NOT_FOUND_MESSAGE);
        }
        return entities;
    }
}