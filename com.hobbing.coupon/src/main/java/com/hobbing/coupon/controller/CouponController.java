package com.hobbing.coupon.controller;

import com.hobbing.coupon.dto.CreateCouponRequest;
import com.hobbing.coupon.service.CouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping
    public ResponseEntity<?> createCoupon(@RequestBody CreateCouponRequest request) {
        return ResponseEntity.ok(couponService.createCoupon(request));
    }

}
