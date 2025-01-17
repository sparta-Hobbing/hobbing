package com.hobbing.coupon.repository;

import com.hobbing.coupon.entity.Coupon;
import com.hobbing.coupon.entity.DiscountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void saveCouponTest() {
        // 1. 새로운 Coupon 생성
        Coupon coupon = new Coupon();
        coupon.setCouponName("Test Coupon");
        coupon.setDiscountType(DiscountType.FIXED);
        coupon.setDiscountAmount(BigDecimal.valueOf(10.00));
        coupon.setMinOrder(BigDecimal.valueOf(50.00));
        coupon.setIssueStart(LocalDateTime.now());
        coupon.setIssueDeadline(LocalDateTime.now().plusDays(7));
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));
        coupon.setMaxIssue(100);
        coupon.setCreatedBy(UUID.randomUUID());

        // 2. 저장
        Coupon savedCoupon = couponRepository.save(coupon);

        // 3. 저장된 결과 확인
        assertThat(savedCoupon).isNotNull();
        assertThat(savedCoupon.getCouponId()).isNotNull();
        assertThat(savedCoupon.getCouponName()).isEqualTo("Test Coupon");
    }

    @Test
    void findCouponByIdTest() {
        // 1. 저장된 Coupon 생성
        Coupon coupon = new Coupon();
        coupon.setCouponName("Find Test Coupon");
        coupon.setDiscountType(DiscountType.FIXED);
        coupon.setDiscountAmount(BigDecimal.valueOf(20.00));
        coupon.setMinOrder(BigDecimal.valueOf(100.00));
        coupon.setIssueStart(LocalDateTime.now());
        coupon.setIssueDeadline(LocalDateTime.now().plusDays(7));
        coupon.setExpirationDate(LocalDateTime.now().plusDays(30));
        coupon.setMaxIssue(50);
        coupon.setCreatedBy(UUID.randomUUID());
        Coupon savedCoupon = couponRepository.save(coupon);

        // 2. ID로 조회
        Coupon foundCoupon = couponRepository.findById(savedCoupon.getCouponId()).orElse(null);

        // 3. 조회된 결과 확인
        assertThat(foundCoupon).isNotNull();
        assertThat(foundCoupon.getCouponId()).isEqualTo(savedCoupon.getCouponId());
        assertThat(foundCoupon.getCouponName()).isEqualTo("Find Test Coupon");
    }
}
