package com.hobbing.coupon.dto;

import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.model.DiscountType;
import com.hobbing.coupon.model.UserCoupon;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
public class UserCouponResponse {
    private String userCouponId;       // 사용자 쿠폰 ID
    private String couponName;         // 쿠폰 이름
    private DiscountType discountType; // 할인 유형 (정액, 정률)
    private BigDecimal discountAmount; // 정액 할인 금액
    private BigDecimal discountRate;   // 정률 할인 비율
    private LocalDateTime expirationDate; // 쿠폰 만료 날짜
    private CouponStatus status;       // 쿠폰 상태 (ACTIVE, USED, EXPIRED)

    public UserCouponResponse(UserCoupon userCoupon) {
        this.userCouponId = userCoupon.getUserCouponId().toString();
        this.couponName = userCoupon.getCoupon().getCouponName();
        this.discountType = userCoupon.getCoupon().getDiscountType();
        this.discountAmount = userCoupon.getCoupon().getDiscountAmount();
        this.discountRate = userCoupon.getCoupon().getDiscountRate();
        this.expirationDate = userCoupon.getExpirationDate();
        this.status = userCoupon.getStatus();
    }
}
