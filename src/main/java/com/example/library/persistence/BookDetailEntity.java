package com.example.library.persistence;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "book_detail")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = false)
public class BookDetailEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    @Column(name = "description", length = 100)
    private String description;

    @Column(name = "publisher", length = 50)
    private String publisher;

    @Column(name = "page_count" , nullable = false)
    private Integer pageCount;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "book_id", unique = true)
    private BookEntity book;
}