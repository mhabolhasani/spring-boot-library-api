package com.example.library.persistance;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "book")
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(nullable = false, length = 50)
    private String title;

    @Column(unique = true, length = 20)
    private String isbn;

    @Column(name = "published_year")
    private Integer publishedYear;

    @ManyToOne
    @JoinColumn(name = "author_id")
    private Author author;

    @OneToOne(mappedBy = "book", cascade = CascadeType.ALL)
    private BookDetail bookDetail;

    @ManyToMany
    @JoinTable(
            name = "book_category",
            joinColumns = @JoinColumn(name = "book_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id")
    )
    private List<Category> categories = new ArrayList<>();

    @OneToMany(mappedBy = "book")
    private List<Loan> loans = new ArrayList<>();

    public Book() {
    }
}