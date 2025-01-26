package com.hobbing.coupon.dto;

import com.hobbing.coupon.model.Coupon;
import com.hobbing.coupon.model.DiscountType;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class CouponResponse {
    private String couponId;
    private String couponName;
    private DiscountType discountType;
    private BigDecimal discountAmount;
    private BigDecimal discountRate;
    private BigDecimal minOrder;
    private LocalDateTime issueStart;
    private LocalDateTime issueDeadline;
    private LocalDateTime expirationDate;
    private int maxIssue;
    private int issuedCount;

    public CouponResponse(Coupon coupon) {
        this.couponId = coupon.getCouponId().toString();
        this.couponName = coupon.getCouponName();
        this.discountType = coupon.getDiscountType();
        this.discountAmount = coupon.getDiscountAmount();
        this.discountRate = coupon.getDiscountRate();
        this.minOrder = coupon.getMinOrder();
        this.issueStart = coupon.getIssueStart();
        this.issueDeadline = coupon.getIssueDeadline();
        this.expirationDate = coupon.getExpirationDate();
        this.maxIssue = coupon.getMaxIssue();
        this.issuedCount = coupon.getIssuedCount();
    }
}