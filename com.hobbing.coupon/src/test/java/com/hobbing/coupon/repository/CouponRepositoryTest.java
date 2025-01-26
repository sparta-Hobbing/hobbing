package com.hobbing.coupon.repository;

import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.model.DiscountType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class CouponRepositoryTest {

    @Autowired
    private CouponRepository couponRepository;

    @Test
    void testSaveAndFindById() {
        // Given
        Coupon coupon = new Coupon();
        coupon.setCouponId(UUID.randomUUID());
        coupon.setCouponName("Repository Test");
        coupon.setDiscountType(DiscountType.AMOUNT);
        coupon.setDiscountAmount(BigDecimal.TEN);
        coupon.setMinOrder(BigDecimal.valueOf(50));
        coupon.setIssueStart(LocalDateTime.now());
        coupon.setIssueDeadline(LocalDateTime.now().plusDays(10));
        coupon.setExpirationDate(LocalDateTime.now().plusDays(20));
        coupon.setMaxIssue(100);
        coupon.setIssuedCount(0);
        coupon.setStatus(CouponStatus.ACTIVE);

        couponRepository.save(coupon);

        // When
        Optional<Coupon> found = couponRepository.findById(coupon.getCouponId());

        // Then
        assertTrue(found.isPresent());
        assertEquals("Repository Test", found.get().getCouponName());
    }
}
