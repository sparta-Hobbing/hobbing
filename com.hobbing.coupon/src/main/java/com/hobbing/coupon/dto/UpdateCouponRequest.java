package com.hobbing.coupon.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Setter
@Getter
public class UpdateCouponRequest {

    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minOrder;
    private LocalDateTime issueDeadline;
    private LocalDateTime expirationDate;
    private int maxIssue;

}
