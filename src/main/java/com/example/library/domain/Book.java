package com.example.library.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Integer id;
    private String title;
    private String isbn;
    private Integer publishedYear;
    private Author author;
    private List<Category> categories;
}
