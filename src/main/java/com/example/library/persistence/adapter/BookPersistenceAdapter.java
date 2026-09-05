package com.example.library.persistence.adapter;

import com.example.library.service.domain.Book;
import com.example.library.persistence.mapper.BookMapper;
import com.example.library.exception.ValidationException;
import com.example.library.persistence.entity.*;
import com.example.library.persistence.repository.*;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.criteria.*;
import org.springframework.data.jpa.domain.PredicateSpecification;
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

    @PersistenceContext
    private EntityManager entityManager;

    public BookPersistenceAdapter(BookRepository bookRepository,
                                  AuthorRepository authorRepository,
                                  CategoryRepository categoryRepository) {
        this.bookRepository = bookRepository;
        this.authorRepository = authorRepository;
        this.categoryRepository = categoryRepository;
    }

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
            Integer maxPageCount,
            Boolean isAvailable
    ) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<BookEntity> query = cb.createQuery(BookEntity.class);
        Root<BookEntity> book = query.from(BookEntity.class);

        PredicateSpecification<BookEntity> spec = hasTitle(title)
                .and(hasAuthor(author))
                .and(hasIsbn(isbn))
                .and(hasMaxPageCount(maxPageCount, query))
                .and(isAvailable(isAvailable, query));

        query.select(book)
                .where(spec.toPredicate(book, cb))
                .distinct(true);

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

    public PredicateSpecification<BookEntity> hasTitle(String title) {
        return (from, builder) -> {
            if (title == null || title.isBlank()) {
                return builder.conjunction();
            }
            return builder.like(builder.lower(from.get("title")),
                    "%" + title.toLowerCase() + "%");
        };
    }

    public PredicateSpecification<BookEntity> hasAuthor(String authorName) {
        return (from, builder) -> {
            if (authorName == null || authorName.isBlank()) {
                return builder.conjunction();
            }
            Join<BookEntity, AuthorEntity> authorJoin = from.join("author");
            return builder.like(builder.lower(authorJoin.get("name")),
                    "%" + authorName.toLowerCase() + "%");
        };
    }

    public PredicateSpecification<BookEntity> hasIsbn(String isbn) {
        return (from, builder) -> {
            if (isbn == null || isbn.isBlank()) {
                return builder.conjunction();
            }
            return builder.equal(from.get("isbn"),
                    isbn);
        };
    }

    public PredicateSpecification<BookEntity> hasMaxPageCount(Integer maxPageCount, CriteriaQuery<?> query) {
        return (book, builder) -> {
            if (maxPageCount == null) {
                return builder.conjunction();
            }
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<BookDetailEntity> detail = subquery.from(BookDetailEntity.class);
            subquery.select(detail.get("id"));
            subquery.where(
                    builder.equal(detail.get("book"), book),
                    builder.lessThanOrEqualTo(detail.get("pageCount"), maxPageCount)
            );
            return builder.exists(subquery);
        };
    }

    public PredicateSpecification<BookEntity> isAvailable(Boolean isAvailable, CriteriaQuery<?> query) {
        return (book, builder) -> {
            if (isAvailable == null) {
                return builder.conjunction();
            }
            Subquery<Long> subquery = query.subquery(Long.class);
            Root<LoanEntity> loan = subquery.from(LoanEntity.class);
            subquery.select(loan.get("id"));
            subquery.where(
                    builder.equal(loan.get("book"), book),
                    builder.isNull(loan.get("returnDate"))
            );
            Predicate hasActiveLoan = builder.exists(subquery);
            return isAvailable ? builder.not(hasActiveLoan) : hasActiveLoan;
        };
    }
}