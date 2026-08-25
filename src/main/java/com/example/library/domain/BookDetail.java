package com.example.library.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetail {
    private Long id;
    private String description;
    private String publisher;
    private Integer pageCount;
    private Long bookId;
}
