package com.example.library.persistence.adapter;

import com.example.library.persistence.mapper.AuthorMapper;
import com.example.library.persistence.repository.AuthorRepository;
import com.example.library.service.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthorPersistenceAdapter {

    private final AuthorRepository authorRepository;

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
        return authorRepository.findAuthorsByLoanCountDesc().stream()
                .map(AuthorMapper::toDomain)
                .toList();
    }
}