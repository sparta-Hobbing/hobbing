package com.hobbing.coupon.controller;

import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.dto.UseCouponRequest;
import com.hobbing.coupon.dto.UserCouponResponse;
import com.hobbing.coupon.service.UserCouponService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
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

    @PostMapping("/issue") // 쿠폰 발급
    public ResponseEntity<ApiResponse<UserCouponResponse>> issueCouponToUser(@RequestBody IssueCouponRequest request) {
        UserCouponResponse response = userCouponService.issueCouponToUser(request);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon issued to user successfully", response));
    }

    @PostMapping("/use") // 쿠폰 사용
    public ResponseEntity<ApiResponse<String>> useCoupon(@RequestBody UseCouponRequest request) {
        userCouponService.useCoupon(request);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon used successfully", null));
    }

    @PostMapping("/{userCouponId}/restore") // 결제 취소 시 쿠폰 복원
    public ResponseEntity<ApiResponse<String>> restoreCouponAfterCancellation(
            @PathVariable UUID userCouponId,
            @AuthenticationPrincipal UserDetails userDetails) {
        String username = userDetails.getUsername();
        userCouponService.restoreCouponAfterPaymentCancellation(userCouponId, username);
        return ResponseEntity.ok(new ApiResponse<>("SUCCESS", "Coupon restored successfully", null));
    }
}
