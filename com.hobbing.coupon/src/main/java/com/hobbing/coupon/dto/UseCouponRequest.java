package com.hobbing.coupon.dto;

import lombok.Data;

import java.util.UUID;

@Data
public class UseCouponRequest {
    private UUID userCouponId; // 사용하려는 쿠폰 ID
    private UUID userId;       // 사용자 ID
}
