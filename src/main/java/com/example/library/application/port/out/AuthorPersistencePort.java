package com.example.library.application.port.out;

import com.example.library.domain.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorPersistencePort {

    List<Author> findAuthorsWithNoLoans();

    List<Author> findAuthorsWithNoLoansSince(LocalDate fromDate);

    List<Author> findAuthorsOrderByLoanCountDesc();
}