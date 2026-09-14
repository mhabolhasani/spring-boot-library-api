package com.example.library.application.port.in;

import com.example.library.domain.Author;

import java.time.LocalDate;
import java.util.List;

public interface AuthorUseCase {

    List<Author> findAuthorsWithNoLoans();

    List<Author> findAuthorsWithNoLoansSince(LocalDate fromDate);

    List<Author> findAuthorsByLoanCountDesc();
}