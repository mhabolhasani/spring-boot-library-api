package com.example.library.domain;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Member {
    private Long id;
    private String name;
    private String email;
    private Long referredById;
}
