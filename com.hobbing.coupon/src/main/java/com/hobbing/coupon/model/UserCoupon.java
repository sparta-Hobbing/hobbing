package com.hobbing.coupon.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "p_usercoupon")
@Getter
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
    private CouponStatus status;

    @Column(name = "issued_at", nullable = false, updatable = false)
    @CreationTimestamp
    private LocalDateTime issuedAt;

    @Column(name = "used_at")
    private LocalDateTime usedAt;

    @Column(name = "restored_at")
    private LocalDateTime restoredAt;
}
