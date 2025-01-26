package com.hobbing.coupon.service;

import com.hobbing.coupon.model.UserCoupon;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CouponExpirationService {

    private final UserCouponService userCouponService;

    public CouponExpirationService(UserCouponService userCouponService) {
        this.userCouponService = userCouponService;
    }

    // 주기적으로 만료된 쿠폰을 비활성화
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void expireCoupons() {
        List<UserCoupon> expiredCoupons = userCouponService.getExpiredCoupons();
        for (UserCoupon userCoupon : expiredCoupons) {
            userCouponService.expireCoupon(userCoupon.getUserCouponId());
        }
    }
}
