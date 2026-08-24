package com.example.library.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class BookDetail {
    private Integer id;
    private String description;
    private String publisher;
    private Integer pageCount;
    private Integer bookId;
}
