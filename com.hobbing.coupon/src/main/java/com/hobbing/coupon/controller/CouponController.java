package com.hobbing.coupon.controller;

import com.hobbing.coupon.dto.*;
import com.hobbing.coupon.model.CouponStatus;
import com.hobbing.coupon.service.CouponService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/coupons")
public class CouponController {

    private static final Logger logger = LoggerFactory.getLogger(CouponController.class);
    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @PostMapping // 쿠폰 생성
    public ApiResponse<CouponResponse> createCoupon(@RequestBody CreateCouponRequest request) {
        logger.info("Received request to create coupon: {}", request);
        CouponResponse response = couponService.createCoupon(request);
        return ApiResponse.ofSuccess("Coupon created successfully", response);
    }

    @PutMapping("/{couponId}") // 쿠폰 수정
    public ApiResponse<CouponResponse> updateCoupon(@PathVariable UUID couponId, @RequestBody UpdateCouponRequest request) {
        logger.info("Received request to update coupon: id={}, request={}", couponId, request);
        CouponResponse response = couponService.updateCoupon(couponId, request);
        return ApiResponse.ofSuccess("Coupon updated successfully", response);
    }

    @DeleteMapping("/{couponId}") // 쿠폰 삭제
    public ApiResponse<String> deleteCoupon(@PathVariable UUID couponId) {
        logger.info("Received request to delete coupon: id={}", couponId);
        couponService.deleteCoupon(couponId);
        return ApiResponse.ofSuccess("Coupon deleted successfully", null);
    }

    @GetMapping // 쿠폰 목록 조회 (관리자 전용)
    public ApiResponse<PageResponse<CouponResponse>> getCoupons(
            @RequestParam(required = false) CouponStatus status,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size) {
        logger.info("Received request to list coupons: status={}, page={}, size={}", status, page, size);
        PageResponse<CouponResponse> response = couponService.getCoupons(status, page, size);
        return ApiResponse.ofSuccess("Coupons fetched successfully", response);
    }
}
