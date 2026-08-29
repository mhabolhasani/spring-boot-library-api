package com.example.library.persistence.repository;

import com.example.library.persistence.entity.AuthorEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface AuthorRepository extends JpaRepository<AuthorEntity, Long> {
    @Query("""
        select a from AuthorEntity a
        left join LoanEntity l on l.book.author = a
        where l.id is null
        """)
    List<AuthorEntity> findAuthorsWithNoLoans();

    @Query("""
        select a from AuthorEntity a
        left join LoanEntity l on l.book.author = a and l.loanDate >= :fromDate
        where l.id is null
        """)
    List<AuthorEntity> findAuthorsWithNoLoansSince(@Param("fromDate") LocalDate fromDate);

    @Query("""
        select a
        from AuthorEntity a
        left join LoanEntity l on l.book.author = a
        group by a
        order by count(l) desc
        """)
    List<AuthorEntity> findAuthorsByLoanCountDesc();
}