package com.jetty.ssafficebe.user.entity;

import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MappedSuperclass;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@EntityListeners(AuditingEntityListener.class)
@MappedSuperclass
@Getter
@Setter
public class BaseEntity {

    @CreatedDate
    private LocalDateTime createdAt;

    @CreatedBy
    private Long createdId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "createdId", insertable = false, updatable = false)
    private User createdUser;

    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @LastModifiedBy
    private LocalDateTime updatedId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "updatedId", insertable = false, updatable = false)
    private User updatedUser;
}
