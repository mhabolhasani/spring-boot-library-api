package com.example.library.dto;

public record BookResponse(
        long id,
        String name,
        String author
) {}