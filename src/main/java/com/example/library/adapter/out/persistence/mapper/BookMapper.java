package com.example.library.adapter.out.persistence.mapper;

import com.example.library.domain.Book;
import com.example.library.adapter.out.persistence.entity.BookEntity;
import com.example.library.adapter.out.persistence.entity.CategoryEntity;

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
                .authorId(entity.getAuthor().getId())
                .categories(entity.getCategories().stream()
                        .map(CategoryEntity::getId)
                        .toList())
                .build();
    }
}