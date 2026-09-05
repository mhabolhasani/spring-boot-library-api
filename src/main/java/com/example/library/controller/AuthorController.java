package com.example.library.controller;

import com.example.library.service.AuthorService;
import com.example.library.service.domain.Author;
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

    private final AuthorService authorService;

    public AuthorController(AuthorService authorService) {
        this.authorService = authorService;
    }

    @GetMapping("/without-loans")
    public ResponseEntity<List<Author>> findAuthorsWithNoLoans() {
        return ResponseEntity.ok(
                authorService.findAuthorsWithNoLoans()
        );
    }

    @GetMapping("/without-loans/since")
    public ResponseEntity<List<Author>> findAuthorsWithNoLoansSince(
            @RequestParam LocalDate fromDate
    ) {
        return ResponseEntity.ok(
                authorService.findAuthorsWithNoLoansSince(fromDate)
        );
    }

    @GetMapping("/by-loans")
    public ResponseEntity<List<Author>> findAuthorsByLoanCountDesc() {
        return ResponseEntity.ok(
                authorService.findAuthorsByLoanCountDesc()
        );
    }
}