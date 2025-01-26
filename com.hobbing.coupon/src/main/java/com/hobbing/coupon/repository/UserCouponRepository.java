package com.hobbing.coupon.repository;

import com.hobbing.coupon.model.UserCoupon;
import com.hobbing.coupon.model.CouponStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UUID> {

    // 특정 사용자의 쿠폰 목록 조회
    List<UserCoupon> findByUserId(UUID userId);

    // 특정 사용자의 특정 상태의 쿠폰 목록 조회
    List<UserCoupon> findByUserIdAndStatus(UUID userId, CouponStatus status);

    // 만료된 쿠폰 조회 (상태가 ACTIVE이면서 만료된 경우)
    @Query("SELECT uc FROM UserCoupon uc WHERE uc.expirationDate < CURRENT_TIMESTAMP AND uc.status = :status")
    List<UserCoupon> findExpiredCoupons();

    // 특정 쿠폰 사용 여부 확인
    @Query("SELECT COUNT(uc) > 0 FROM UserCoupon uc WHERE uc.userCouponId = :userCouponId AND uc.status = :status")
    boolean isCouponUsed(@Param("userCouponId") UUID userCouponId);

    // 특정 사용자의 특정 쿠폰 조회
    Optional<UserCoupon> findByUserIdAndCouponId(UUID userId, UUID couponId);
}
