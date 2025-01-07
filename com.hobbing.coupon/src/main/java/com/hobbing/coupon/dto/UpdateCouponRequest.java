package com.hobbing.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateCouponRequest {
    private String couponName;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minOrder;
    private LocalDateTime issueDeadline;
    private LocalDateTime expirationDate;
    private int maxIssue;
}
