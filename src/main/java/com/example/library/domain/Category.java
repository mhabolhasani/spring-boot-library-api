package com.example.library.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Category {
    private Long id;
    private String name;
}
