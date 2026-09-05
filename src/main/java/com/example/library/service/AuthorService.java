package com.example.library.service;

import com.example.library.persistence.adapter.AuthorPersistenceAdapter;
import com.example.library.persistence.entity.AuthorEntity;
import com.example.library.persistence.repository.AuthorRepository;
import com.example.library.service.domain.Author;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class AuthorService {

    private final AuthorPersistenceAdapter authorPersistenceAdapter;

    public AuthorService(AuthorPersistenceAdapter authorPersistenceAdapter){
        this.authorPersistenceAdapter = authorPersistenceAdapter;
    }

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
