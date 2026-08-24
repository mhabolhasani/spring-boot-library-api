package com.example.library.persistence.adapter;

import com.example.library.domain.Author;
import com.example.library.exception.ValidationException;
import com.example.library.domain.mapper.AuthorMapper;
import com.example.library.persistence.entity.AuthorEntity;
import com.example.library.persistence.repository.AuthorRepository;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class AuthorPersistenceAdapter {

    private static final String NOT_FOUND_CODE = "author_id_invalid";
    private static final String NOT_FOUND_MESSAGE = "author not found";

    private final AuthorRepository authorRepository;

    public AuthorPersistenceAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAll() {
        return authorRepository.findAll().stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }

    public Optional<Author> findById(Integer id) {
        return authorRepository.findById(id).map(AuthorMapper::toDomain);
    }

    public Author save(Author author) {
        AuthorEntity entity = author.getId() != null
                ? getEntityOrThrow(author.getId())
                : AuthorEntity.builder().build();

        entity.setName(author.getName());
        entity.setBirthYear(author.getBirthYear());

        return AuthorMapper.toDomain(authorRepository.save(entity));
    }

    public void deleteById(Integer id) {
        authorRepository.delete(getEntityOrThrow(id));
    }

    private AuthorEntity getEntityOrThrow(Integer id) {
        return authorRepository.findById(id)
                .orElseThrow(() -> new ValidationException(NOT_FOUND_CODE, NOT_FOUND_MESSAGE));
    }
}
