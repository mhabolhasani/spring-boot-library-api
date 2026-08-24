package com.example.library.domain.mapper;

import com.example.library.domain.Author;
import com.example.library.persistence.entity.AuthorEntity;

public final class AuthorMapper {

    private AuthorMapper() {
    }

    public static Author toDomain(AuthorEntity entity) {
        if (entity == null) {
            return null;
        }
        return Author.builder()
                .id(entity.getId())
                .name(entity.getName())
                .birthYear(entity.getBirthYear())
                .build();
    }
}
