package com.example.library.service.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Author {
    private Long id;
    private String name;
    private Integer birthYear;
}
