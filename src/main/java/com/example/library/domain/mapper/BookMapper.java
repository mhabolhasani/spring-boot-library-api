package com.example.library.domain.mapper;

import com.example.library.domain.Book;
import com.example.library.persistence.entity.BookEntity;

public final class BookMapper {

    private BookMapper() {
    }

    public static Book toDomain(BookEntity entity) {
        if (entity == null) {
            return null;
        }
        return Book.builder()
                .id(entity.getId())
                .title(entity.getTitle())
                .isbn(entity.getIsbn())
                .publishedYear(entity.getPublishedYear())
                .author(AuthorMapper.toDomain(entity.getAuthor()))
                .categories(entity.getCategories().stream()
                        .map(CategoryMapper::toDomain)
                        .toList())
                .build();
    }
}
