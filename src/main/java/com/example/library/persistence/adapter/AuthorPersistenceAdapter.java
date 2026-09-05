package com.example.library.persistence.adapter;

import com.example.library.persistence.mapper.AuthorMapper;
import com.example.library.persistence.repository.AuthorRepository;
import com.example.library.service.domain.Author;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
public class AuthorPersistenceAdapter {

    private final AuthorRepository authorRepository;

    public AuthorPersistenceAdapter(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> findAuthorsWithNoLoans() {
        return authorRepository.findAuthorsWithNoLoans().stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }

    public List<Author> findAuthorsWithNoLoansSince(LocalDate fromDate) {
        return authorRepository.findAuthorsWithNoLoansSince(fromDate).stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }

    public List<Author> findAuthorsOrderByLoanCountDesc() {
        return authorRepository.findAuthorsOrderByLoanCountDesc().stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }
}