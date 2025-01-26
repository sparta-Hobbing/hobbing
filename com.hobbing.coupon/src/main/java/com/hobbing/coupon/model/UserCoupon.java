package com.hobbing.coupon.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;  // Lombok @Setter 추가
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_usercoupon")
@Getter
@Setter  // Lombok을 사용하여 setter 메서드 자동 생성
@NoArgsConstructor
public class UserCoupon {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(name = "UUID", strategy = "org.hibernate.id.UUIDGenerator")
    @Column(name = "user_coupon_id", updatable = false, nullable = false)
    private UUID userCouponId;

    @Column(name = "user_id", nullable = false)
    private UUID userId;

    @ManyToOne
    @JoinColumn(name = "coupon_id", nullable = false)
    private Coupon coupon;
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private CouponStatus status;  // 상태: ACTIVE, USED, EXPIRED

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;

    @Column(name = "created_at", updatable = false, nullable = false)
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "created_by", updatable = false, nullable = false)
    private String createdBy;

    @Column(name = "expiration_date")
    private LocalDateTime expirationDate;

    // 쿠폰 사용 처리
    public void use() {
        if (this.status != CouponStatus.ACTIVE) {
            throw new IllegalStateException("Coupon is not active");
        }
        this.status = CouponStatus.USED;
        this.usedAt = LocalDateTime.now();
    }

    // 쿠폰 복원 처리
    public void restore() {
        if (this.status != CouponStatus.USED) {
            throw new IllegalStateException("Only used coupons can be restored");
        }
        this.status = CouponStatus.ACTIVE;
        this.restoredAt = LocalDateTime.now();
    }

    // 쿠폰 만료 처리
    public void expire() {
        if (this.status == CouponStatus.ACTIVE) {
            this.status = CouponStatus.EXPIRED;
        }
    }

    // 상태 검증 (선택적, 다른 상태로 인한 처리 로직)
    private boolean isStatusValid(CouponStatus status) {
        return status != null && status != CouponStatus.EXPIRED;  // 상태가 EXPIRED가 아니어야 유효
    }
}
