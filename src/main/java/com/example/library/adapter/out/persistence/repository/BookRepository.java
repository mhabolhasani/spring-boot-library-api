package com.example.library.adapter.out.persistence.repository;

import com.example.library.adapter.out.persistence.entity.BookEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookRepository extends JpaRepository<BookEntity, Long> {
}