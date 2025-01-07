package com.hobbing.coupon.service;

import com.hobbing.coupon.dto.CreateCouponRequest;
import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.entity.Coupon;
import com.hobbing.coupon.entity.CouponStatus;
import com.hobbing.coupon.entity.UserCoupon;
import com.hobbing.coupon.repository.CouponRepository;
import com.hobbing.coupon.repository.UserCouponRepository;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final UserCouponRepository userCouponRepository;

    public CouponService(CouponRepository couponRepository, UserCouponRepository userCouponRepository) {
        this.couponRepository = couponRepository;
        this.userCouponRepository = userCouponRepository;
    }

    public Coupon createCoupon(CreateCouponRequest request) {
        Coupon coupon = new Coupon();
        coupon.setCouponName(request.getCouponName());
        coupon.setDiscountType(request.getDiscountType());
        coupon.setDiscountAmount(request.getDiscountAmount());
        coupon.setDiscountRate(request.getDiscountRate());
        coupon.setMinOrder(request.getMinOrder());
        coupon.setIssueStart(request.getIssueStart());
        coupon.setIssueDeadline(request.getIssueDeadline());
        coupon.setExpirationDate(request.getExpirationDate());
        coupon.setMaxIssue(request.getMaxIssue());
        coupon.setIssuedCount(0);

        return couponRepository.save(coupon);
    }

}
