package com.hobbing.coupon.dto;

import com.hobbing.coupon.entity.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateCouponRequest {
    private String couponName;
    private DiscountType discountType;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minOrder;
    private LocalDateTime issueStart;
    private LocalDateTime issueDeadline;
    private LocalDateTime expirationDate;
    private int maxIssue;
}
