package com.example.library.domain.mapper;

import com.example.library.domain.BookDetail;
import com.example.library.persistence.entity.BookDetailEntity;

public final class BookDetailMapper {

    private BookDetailMapper() {
    }

    public static BookDetail toDomain(BookDetailEntity entity) {
        if (entity == null) {
            return null;
        }
        return BookDetail.builder()
                .id(entity.getId())
                .description(entity.getDescription())
                .publisher(entity.getPublisher())
                .pageCount(entity.getPageCount())
                .bookId(entity.getBook() != null ? entity.getBook().getId() : null)
                .build();
    }
}
