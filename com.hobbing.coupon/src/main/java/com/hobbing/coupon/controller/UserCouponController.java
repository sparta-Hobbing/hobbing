package com.hobbing.coupon.controller;

import com.hobbing.coupon.dto.ApiResponse;
import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.dto.UseCouponRequest;
import com.hobbing.coupon.dto.UserCouponResponse;
import com.hobbing.coupon.model.UserCoupon;
import com.hobbing.coupon.service.UserCouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/user-coupons")
public class UserCouponController {

    private final UserCouponService userCouponService;

    public UserCouponController(UserCouponService userCouponService) {
        this.userCouponService = userCouponService;
    }

    // 쿠폰 발급
    @PostMapping("/issue")
    public ResponseEntity<ApiResponse<UserCouponResponse>> issueCouponToUser(@RequestBody IssueCouponRequest request) {
        UserCouponResponse response = userCouponService.issueCouponToUser(request);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon issued to user successfully", response));
    }

    //쿠폰 사용
    @PostMapping("/use")
    public ResponseEntity<ApiResponse<String>> useCoupon(@RequestBody UseCouponRequest request) {
        userCouponService.useCoupon(request);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon used successfully", null));
    }
    // 결제 취소 시 쿠폰 복원
    @PostMapping("/{userCouponId}/restore")
    public ResponseEntity<ApiResponse<String>> restoreCouponAfterCancellation(@PathVariable UUID userCouponId) {
        userCouponService.restoreCoupon(userCouponId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon restored successfully", null));
    }

    // 특정 사용자의 쿠폰 목록 조회
    @GetMapping("/user/{userId}")
    public ResponseEntity<ApiResponse<List<UserCoupon>>> getUserCoupons(@PathVariable UUID userId) {
        List<UserCoupon> userCoupons = userCouponService.getUserCoupons(userId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "User coupons fetched successfully", userCoupons));
    }

    // 만료된 쿠폰 목록 조회
    @GetMapping("/expired")
    public ResponseEntity<ApiResponse<List<UserCoupon>>> getExpiredCoupons() {
        List<UserCoupon> expiredCoupons = userCouponService.getExpiredCoupons();
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Expired coupons fetched successfully", expiredCoupons));
    }

    // 쿠폰 사용 여부 확인
    @GetMapping("/{userCouponId}/is-used")
    public ResponseEntity<ApiResponse<Boolean>> isCouponUsed(@PathVariable UUID userCouponId) {
        boolean isUsed = userCouponService.checkCouponUsed(userCouponId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon usage status fetched", isUsed));
    }

    // 쿠폰 만료
    @PostMapping("/{userCouponId}/expire")
    public ResponseEntity<ApiResponse<String>> expireCoupon(@PathVariable UUID userCouponId) {
        userCouponService.expireCoupon(userCouponId);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon expired successfully", null));
    }
}
