package com.example.library.persistence;

import jakarta.persistence.*;

@Entity
@Table(name = "bookDetail")
public class BookDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(length = 100)
    private String description;

    @Column(length = 50)
    private String publisher;

    @Column(name = "page_count")
    private Integer pageCount;

    @OneToOne
    @JoinColumn(name = "book_id", unique = true)
    private Book book;

    public BookDetail() {
    }
}