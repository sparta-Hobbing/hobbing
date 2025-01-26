package com.hobbing.coupon.dto;

import lombok.Data;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;


import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class IssueCouponRequest {
    private UUID couponId; // 발급할 쿠폰 ID
    private UUID userId;   // 쿠폰을 받을 사용자 ID
}
