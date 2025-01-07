package com.hobbing.coupon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_coupon")
@Getter
@NoArgsConstructor
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "coupon_id", updatable = false, nullable = false)
    private UUID couponId;

    @Column(name = "coupon_name", nullable = false)
    private String couponName;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(name = "discount_amount", precision = 10, scale = 2, nullable = true, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal discountAmount;

    @Column(name = "discount_rate", precision = 10, scale = 2, nullable = true, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal discountRate;

    @Column(name = "min_order", precision = 10, scale = 2, nullable = false, columnDefinition = "DECIMAL(10,2) DEFAULT 0.00")
    private BigDecimal minOrder;

    @Column(name = "issue_start", nullable = false)
    private LocalDateTime issueStart;

    @Column(name = "issue_deadline", nullable = false)
    private LocalDateTime issueDeadline;

    @Column(name = "expiration_date", nullable = false)
    private LocalDateTime expirationDate;

    @Column(name = "max_issue", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int maxIssue;

    @Column(name = "issued_count", nullable = false, columnDefinition = "INT DEFAULT 0")
    private int issuedCount;

    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "created_by", nullable = false)
    private UUID createdBy;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    @Setter // 수정 가능
    @Column(name = "updated_by")
    private UUID updatedBy;

    @Setter // 상태 관리 가능
    @Column(name = "is_deleted", nullable = false, columnDefinition = "BOOLEAN DEFAULT FALSE")
    private Boolean isDeleted;

    @Setter // 삭제 시간 설정 가능
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    @Setter // 삭제자 설정 가능
    @Column(name = "deleted_by")
    private UUID deletedBy;

    // issuedCount 업데이트 로직 추가
    public void incrementIssuedCount() {
        if (this.issuedCount < this.maxIssue) {
            this.issuedCount++;
        } else {
            throw new IllegalStateException("Maximum issue count reached.");
        }
    }
}
