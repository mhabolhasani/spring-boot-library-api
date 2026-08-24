package com.example.library.persistence.repository;

import com.example.library.persistence.entity.BookDetailEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookDetailRepository extends JpaRepository<BookDetailEntity, Integer> {
}
