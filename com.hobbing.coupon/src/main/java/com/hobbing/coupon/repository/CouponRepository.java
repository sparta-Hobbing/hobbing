package com.hobbing.coupon.repository;

import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.CouponStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface CouponRepository extends JpaRepository<Coupon, UUID> {

    // 쿠폰 이름 중복 확인
    boolean existsByCouponNameAndIsDeletedFalse(String couponName);

    // 활성화된 쿠폰 조회 (삭제되지 않은 쿠폰)
    Optional<Coupon> findByCouponNameAndIsDeletedFalse(String couponName);

    Page<Coupon> findByStatus(CouponStatus status, Pageable pageable);
}