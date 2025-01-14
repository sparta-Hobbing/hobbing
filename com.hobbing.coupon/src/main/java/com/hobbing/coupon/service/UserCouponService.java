package com.hobbing.coupon.service;

import com.hobbing.coupon.common.exception.CommonErrorCode;
import com.hobbing.coupon.common.exception.CustomException;
import com.hobbing.coupon.dto.IssueCouponRequest;
import com.hobbing.coupon.dto.UseCouponRequest;
import com.hobbing.coupon.dto.UserCouponResponse;
import com.hobbing.coupon.entity.Coupon;
import com.hobbing.coupon.entity.CouponStatus;
import com.hobbing.coupon.entity.UserCoupon;
import com.hobbing.coupon.repository.CouponRepository;
import com.hobbing.coupon.repository.UserCouponRepository;
import com.hobbing.coupon.repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;
import java.util.List;

@Service
public class UserCouponService {

    private final UserCouponRepository userCouponRepository;
    private final CouponRepository couponRepository;
    private final UserRepository userRepository;
    private final ReentrantLock lock = new ReentrantLock();

    public UserCouponService(UserCouponRepository userCouponRepository, CouponRepository couponRepository, UserRepository userRepository) {
        this.userCouponRepository = userCouponRepository;
        this.couponRepository = couponRepository;
        this.userRepository = userRepository;
    }

    // 사용자에게 쿠폰 발급
@Transactional
public UserCouponResponse issueCouponToUser(IssueCouponRequest request) {
    Coupon coupon = couponRepository.findById(request.getCouponId())
            .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

    // 발급 수 증가 부분에만 락 적용
    lock.lock();
    try {
        if (coupon.getIssuedCount() >= coupon.getMaxIssue()) {
            throw new CustomException(CommonErrorCode.COUPON_ISSUE_LIMIT_REACHED);
        }
        coupon.incrementIssuedCount();
    } finally {
        lock.unlock();
    }

    UserCoupon userCoupon = new UserCoupon();
    userCoupon.setUserId(request.getUserId());
    userCoupon.setCoupon(coupon);
    userCoupon.setStatus(CouponStatus.ACTIVE);
    userCoupon.setExpirationDate(coupon.getExpirationDate());

    userCouponRepository.save(userCoupon);

    return new UserCouponResponse(userCoupon);
}

    // 쿠폰 사용
    @Transactional
    public void useCoupon(UseCouponRequest request) {
        UserCoupon userCoupon = userCouponRepository.findById(request.getUserCouponId())
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        if (!userCoupon.getUserId().equals(request.getUserId())) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (userCoupon.getStatus() != CouponStatus.ACTIVE) {
            throw new CustomException(CommonErrorCode.INVALID_COUPON_STATE);
        }

        userCoupon.setStatus(CouponStatus.USED);
        userCoupon.setUsedAt(LocalDateTime.now());

        userCouponRepository.save(userCoupon);
    }

    // 결제 취소 시 쿠폰 복원
    @Transactional
    public void restoreCouponAfterPaymentCancellation(UUID userCouponId, String username) {
        UserCoupon userCoupon = userCouponRepository.findById(userCouponId)
                .orElseThrow(() -> new CustomException(CommonErrorCode.COUPON_NOT_FOUND));

        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CommonErrorCode.UNAUTHORIZED_ACCESS))
                .getId();

        if (!userCoupon.getUserId().equals(userId)) {
            throw new CustomException(CommonErrorCode.UNAUTHORIZED_ACCESS);
        }

        if (userCoupon.getStatus() != CouponStatus.USED) {
            throw new CustomException(CommonErrorCode.INVALID_COUPON_STATE);
        }

        userCoupon.setStatus(CouponStatus.ACTIVE);
        userCoupon.setRestoredAt(LocalDateTime.now());

        userCouponRepository.save(userCoupon);
    }

    // 만료된 쿠폰 비활성화
    @Transactional
    public void deactivateExpiredCoupons() {
        List<UserCoupon> expiredCoupons = userCouponRepository.findExpiredCoupons();
        for (UserCoupon userCoupon : expiredCoupons) {
            userCoupon.setStatus(CouponStatus.EXPIRED);
            userCouponRepository.save(userCoupon);
        }
    }

    // 로그인된 사용자의 쿠폰 목록 조회
    @Transactional(readOnly = true)
    public List<UserCouponResponse> getUserCouponsByUsername(String username) {
        UUID userId = userRepository.findByUsername(username)
                .orElseThrow(() -> new CustomException(CommonErrorCode.UNAUTHORIZED_ACCESS))
                .getId();

        List<UserCoupon> userCoupons = userCouponRepository.findByUserId(userId);

        return userCoupons.stream()
                .map(UserCouponResponse::new)
                .collect(Collectors.toList());
    }
}
