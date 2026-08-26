package com.example.library.persistence.mapper;

import com.example.library.service.domain.Category;
import com.example.library.persistence.entity.CategoryEntity;

public final class CategoryMapper {

    private CategoryMapper() {
    }

    public static Category toDomain(CategoryEntity entity) {
        if (entity == null) {
            return null;
        }
        return Category.builder()
                .id(entity.getId())
                .name(entity.getName())
                .build();
    }
}
