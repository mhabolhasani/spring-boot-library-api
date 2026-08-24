package com.example.library.persistence.adapter;

import com.example.library.domain.Category;
import com.example.library.exception.ValidationException;
import com.example.library.domain.mapper.CategoryMapper;
import com.example.library.persistence.entity.CategoryEntity;
import com.example.library.persistence.repository.CategoryRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class CategoryPersistenceAdapter {

    private static final String NOT_FOUND_CODE = "category_id_invalid";
    private static final String NOT_FOUND_MESSAGE = "category not found";

    private final CategoryRepository categoryRepository;

    public CategoryPersistenceAdapter(CategoryRepository categoryRepository) {
        this.categoryRepository = categoryRepository;
    }

    public List<Category> findAll() {
        return categoryRepository.findAll().stream()
                .map(CategoryMapper::toDomain)
                .toList();
    }

    public Optional<Category> findById(Integer id) {
        return categoryRepository.findById(id).map(CategoryMapper::toDomain);
    }

    public Category save(Category category) {
        CategoryEntity entity = CategoryEntity.builder().build();
        entity.setName(category.getName());
        return CategoryMapper.toDomain(categoryRepository.save(entity));
    }

    public void deleteById(Integer id) {
        categoryRepository.delete(getEntityOrThrow(id));
    }

    private CategoryEntity getEntityOrThrow(Integer id) {
        return categoryRepository.findById(id)
                .orElseThrow(() -> new ValidationException(NOT_FOUND_CODE, NOT_FOUND_MESSAGE));
    }
}
