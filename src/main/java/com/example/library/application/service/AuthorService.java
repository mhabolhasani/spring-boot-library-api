package com.example.library.application.service;

import com.example.library.application.port.in.AuthorUseCase;
import com.example.library.application.port.out.AuthorPersistencePort;
import com.example.library.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService implements AuthorUseCase {

    private final AuthorPersistencePort authorPersistencePort;

    public List<Author> findAuthorsWithNoLoans() {
        return authorPersistencePort.findAuthorsWithNoLoans();
    }

    public List<Author> findAuthorsWithNoLoansSince(LocalDate fromDate) {
        return authorPersistencePort.findAuthorsWithNoLoansSince(fromDate);
    }

    public List<Author> findAuthorsByLoanCountDesc() {
        return authorPersistencePort.findAuthorsOrderByLoanCountDesc();
    }
}
