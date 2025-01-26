package com.hobbing.coupon.dto;

import com.hobbing.coupon.model.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CreateCouponRequest {
    private String couponName;
    private DiscountType discountType; // 정액/정률
    private BigDecimal discountAmount; // 정액 할인 금액
    private BigDecimal discountRate;   // 정률 할인 비율
    private BigDecimal minOrder;       // 최소 주문 금액
    private LocalDateTime issueStart;  // 발급 시작 시간
    private LocalDateTime issueDeadline; // 발급 종료 시간
    private LocalDateTime expirationDate; // 만료 시간
    private int maxIssue;             // 최대 발급 수량

    public CreateCouponRequest(String testCoupon, DiscountType discountType, BigDecimal bigDecimal, BigDecimal bigDecimal1, BigDecimal bigDecimal2, LocalDateTime now, LocalDateTime localDateTime, LocalDateTime localDateTime1, int i) {
    }
}
