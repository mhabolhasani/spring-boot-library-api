package com.example.library.persistence.mapper;

import com.example.library.service.domain.Member;
import com.example.library.persistence.entity.MemberEntity;

public final class MemberMapper {

    private MemberMapper() {
    }

    public static Member toDomain(MemberEntity entity) {
        if (entity == null) {
            return null;
        }
        return Member.builder()
                .id(entity.getId())
                .name(entity.getName())
                .email(entity.getEmail())
                .referredById(entity.getReferredBy() != null ? entity.getReferredBy().getId() : null)
                .build();
    }
}
