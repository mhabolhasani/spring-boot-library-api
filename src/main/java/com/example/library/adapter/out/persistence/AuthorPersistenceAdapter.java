package com.example.library.adapter.out.persistence;

import com.example.library.application.port.out.AuthorPersistencePort;
import com.example.library.adapter.out.persistence.mapper.AuthorMapper;
import com.example.library.adapter.out.persistence.repository.AuthorRepository;
import com.example.library.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
public class AuthorPersistenceAdapter implements AuthorPersistencePort {

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