package com.example.library.adapter.in.web;

import com.example.library.application.port.in.AuthorUseCase;
import com.example.library.domain.Author;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/authors")
public class AuthorController {

    private final AuthorUseCase authorUseCase;

    public AuthorController(AuthorUseCase authorUseCase) {
        this.authorUseCase = authorUseCase;
    }

    @GetMapping("/without-loans")
    public ResponseEntity<List<Author>> findAuthorsWithNoLoans() {
        return ResponseEntity.ok(
                authorUseCase.findAuthorsWithNoLoans()
        );
    }

    @GetMapping("/without-loans/since")
    public ResponseEntity<List<Author>> findAuthorsWithNoLoansSince(
            @RequestParam LocalDate fromDate
    ) {
        return ResponseEntity.ok(
                authorUseCase.findAuthorsWithNoLoansSince(fromDate)
        );
    }

    @GetMapping("/by-loans")
    public ResponseEntity<List<Author>> findAuthorsByLoanCountDesc() {
        return ResponseEntity.ok(
                authorUseCase.findAuthorsByLoanCountDesc()
        );
    }
}