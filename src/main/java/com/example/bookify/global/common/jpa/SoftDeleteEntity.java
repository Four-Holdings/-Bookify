package com.example.bookify.global.common.jpa;

import jakarta.persistence.MappedSuperclass;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@MappedSuperclass
public abstract class SoftDeleteEntity extends BaseEntity{

    private boolean isDeleted;

    private LocalDateTime deletedAt;

    public void delete() {
        this.isDeleted = true;
        this.deletedAt = LocalDateTime.now();
    }

}
