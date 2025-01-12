package com.hobbing.coupon.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class IssueCouponRequest {
    private UUID couponId; // 발급할 쿠폰 ID
    private UUID userId;   // 쿠폰을 받을 사용자 ID
}
