package com.example.library.service;

import com.example.library.persistence.adapter.AuthorPersistenceAdapter;
import com.example.library.service.domain.Author;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@AllArgsConstructor
public class AuthorService {

    private final AuthorPersistenceAdapter authorPersistenceAdapter;

    public List<Author> findAuthorsWithNoLoans() {
        return authorPersistenceAdapter.findAuthorsWithNoLoans();
    }

    public List<Author> findAuthorsWithNoLoansSince(LocalDate fromDate) {
        return authorPersistenceAdapter.findAuthorsWithNoLoansSince(fromDate);
    }

    public List<Author> findAuthorsByLoanCountDesc() {
        return authorPersistenceAdapter.findAuthorsOrderByLoanCountDesc();
    }
}
