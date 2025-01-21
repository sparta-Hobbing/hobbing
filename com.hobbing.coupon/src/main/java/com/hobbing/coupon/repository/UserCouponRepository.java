package com.hobbing.coupon.repository;

import com.hobbing.coupon.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserCouponRepository extends JpaRepository<UserCoupon, UUID> {

    // 특정 사용자의 쿠폰 목록 조회 (상태 필터링 지원)
    List<UserCoupon> findByUserId(UUID userId);

    // 만료된 쿠폰 조회 (상태가 ACTIVE이면서 만료된 경우)
    @Query("SELECT uc FROM UserCoupon uc WHERE uc.expirationDate < CURRENT_TIMESTAMP AND uc.status = 'ACTIVE'")
    List<UserCoupon> findExpiredCoupons();

    // 특정 쿠폰 사용 여부 확인 (status를 'USED'로 체크)
    @Query("SELECT COUNT(uc) > 0 FROM UserCoupon uc WHERE uc.userCouponId = :userCouponId AND uc.status = 'USED'")
    boolean isCouponUsed(@Param("userCouponId") UUID userCouponId);

    Optional<UserCoupon> findByUserIdAndCouponId(UUID userId, UUID couponId);

}
