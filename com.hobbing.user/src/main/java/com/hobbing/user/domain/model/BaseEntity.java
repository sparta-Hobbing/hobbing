package com.hobbing.user.domain.model;

import jakarta.persistence.Column;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.MappedSuperclass;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Setter
@Getter
@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
public abstract class BaseEntity {

    @CreatedDate
    @Column(nullable = false, updatable = false)
    protected LocalDateTime createdAt;

    @CreatedBy
    @Column(updatable = false)
    protected UUID createdBy;

    @LastModifiedDate
    @Column
    protected LocalDateTime updatedAt;

    @LastModifiedBy
    @Column
    protected UUID updatedBy;

    @Column
    protected LocalDateTime deletedAt;

    @Column
    protected UUID deletedBy;

    @Setter
    @Column(nullable = false)
    protected boolean isDeleted = false;

}