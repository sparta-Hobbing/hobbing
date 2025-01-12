package com.hobbing.coupon.dto;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UpdateCouponRequest {
    private BigDecimal discountAmount; // 수정할 정액 할인 금액
    private BigDecimal discountRate;   // 수정할 정률 할인 비율
    private BigDecimal minOrder;       // 수정할 최소 주문 금액
    private LocalDateTime issueDeadline; // 수정할 발급 종료 시간
    private LocalDateTime expirationDate; // 수정할 만료 시간
    private int maxIssue;             // 수정할 최대 발급 수량
}
