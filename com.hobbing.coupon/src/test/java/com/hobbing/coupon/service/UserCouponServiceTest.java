package com.hobbing.coupon.service;

import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.dto.UserCouponResponse;
import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.model.DiscountType;
import com.hobbing.coupon.model.UserCoupon;
import com.hobbing.coupon.repository.CouponRepository;
import com.hobbing.coupon.repository.UserCouponRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@SpringBootTest
class UserCouponServiceTest {

    @InjectMocks
    private UserCouponService userCouponService;

    @Mock
    private UserCouponRepository userCouponRepository;

    @Mock
    private CouponRepository couponRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void issueCouponToUser_success() {
        // Given
        UUID couponId = UUID.randomUUID();
        UUID userId = UUID.randomUUID();

        Coupon coupon = new Coupon();
        coupon.setCouponId(couponId);
        coupon.setCouponName("Test Coupon");
        coupon.setDiscountType(DiscountType.AMOUNT); // 올바른 Enum 값 설정
        coupon.setExpirationDate(LocalDateTime.now().plusDays(10));

        UserCoupon userCoupon = new UserCoupon();
        userCoupon.setUserCouponId(UUID.randomUUID());
        userCoupon.setUserId(userId);
        userCoupon.setCoupon(coupon);
        userCoupon.setStatus(CouponStatus.ACTIVE);
        userCoupon.setExpirationDate(coupon.getExpirationDate());

        IssueCouponRequest request = new IssueCouponRequest(couponId, userId);

        when(couponRepository.findById(couponId)).thenReturn(Optional.of(coupon));
        when(userCouponRepository.save(any(UserCoupon.class))).thenReturn(userCoupon);

        // When
        UserCouponResponse response = userCouponService.issueCouponToUser(request);

        // Then
        assertNotNull(response);
        assertEquals("Test Coupon", response.getCouponName());
        assertEquals(CouponStatus.ACTIVE, response.getStatus());
        verify(userCouponRepository, times(1)).save(any(UserCoupon.class));
    }
}
