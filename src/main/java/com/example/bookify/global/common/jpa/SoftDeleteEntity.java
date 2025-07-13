package com.example.bookify.global.common.jpa;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class SoftDeleteEntity extends BaseEntity {

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

    // JPA 인식 문제로 게터 직접 생성
    // getIsDeleted로 생성하면 다른 도메인에 영향을 끼치므로 isDeleted로 생성합니다.
    public Boolean isDeleted() {
        return isDeleted;
    }
}
