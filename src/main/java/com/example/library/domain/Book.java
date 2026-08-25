package com.example.library.domain;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Book {
    private Long id;
    private String title;
    private String isbn;
    private Integer publishedYear;
    private Long authorId;
    private List<Long> categories;
}
