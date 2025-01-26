package com.hobbing.coupon.service;

import com.hobbing.coupon.dto.CreateCouponRequest;
import com.hobbing.coupon.dto.CouponResponse;
import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.model.DiscountType;
import com.hobbing.coupon.repository.CouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class CouponServiceTest {

    @InjectMocks
    private CouponService couponService;

    @Mock
    private CouponRepository couponRepository;

    private Coupon coupon;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        coupon = new Coupon();
        coupon.setCouponId(UUID.randomUUID());
        coupon.setCouponName("Test Coupon");
        coupon.setDiscountType(DiscountType.AMOUNT);
        coupon.setDiscountAmount(BigDecimal.TEN);
        coupon.setMinOrder(BigDecimal.valueOf(50));
        coupon.setIssueStart(LocalDateTime.now());
        coupon.setIssueDeadline(LocalDateTime.now().plusDays(10));
        coupon.setExpirationDate(LocalDateTime.now().plusDays(20));
        coupon.setMaxIssue(100);
        coupon.setIssuedCount(0);
        coupon.setStatus(CouponStatus.ACTIVE);
    }

    @Test
    void createCoupon_success() {
        // Given
        CreateCouponRequest request = new CreateCouponRequest(
                "Test Coupon",
                DiscountType.AMOUNT,
                BigDecimal.TEN,
                null,
                BigDecimal.valueOf(50),
                LocalDateTime.now(),
                LocalDateTime.now().plusDays(10),
                LocalDateTime.now().plusDays(20),
                100
        );

        when(couponRepository.save(any(Coupon.class))).thenReturn(coupon);

        // When
        CouponResponse response = couponService.createCoupon(request);

        // Then
        assertNotNull(response);
        assertEquals("Test Coupon", response.getCouponName());
        verify(couponRepository, times(1)).save(any(Coupon.class));
    }
}
