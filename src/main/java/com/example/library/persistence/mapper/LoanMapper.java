package com.example.library.persistence.mapper;

import com.example.library.service.domain.Loan;
import com.example.library.persistence.entity.LoanEntity;

public final class LoanMapper {

    private LoanMapper() {
    }

    public static Loan toDomain(LoanEntity entity) {
        if (entity == null) {
            return null;
        }
        return Loan.builder()
                .id(entity.getId())
                .loanDate(entity.getLoanDate())
                .dueDate(entity.getDueDate())
                .returnDate(entity.getReturnDate())
                .memberId(entity.getMember() != null ? entity.getMember().getId() : null)
                .bookId(entity.getBook() != null ? entity.getBook().getId() : null)
                .build();
    }
}
